package com.example.expensestracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.R
import com.example.expensestracker.adapters.ExpensesAdapter
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.FragmentExpensesBinding
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpensesFragment : Fragment(), ExpensesAdapter.ExpenseClickListener {

    private val binding by viewBinding(FragmentExpensesBinding::inflate)
    private val viewModel: MonthsViewModel by viewModels()
    private val adapter = ExpensesAdapter(this)
    private val args by navArgs<ExpensesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupName()
        configureList()
        observeViewModel()
        configureBackButton()
        setupAddExpenseFAB()
        setupEditMonthFAB()

        return binding.root
    }

    private fun setupName() = with(binding) {
        monthDetailsNameTextView.text = args.monthName
    }

    private fun configureBackButton() = with(binding) {
        viewModel.total.observe(viewLifecycleOwner) {
            viewModel.updateMonth(args.monthId, args.monthName, it)
        }
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.getExpenses(args.monthId)
        viewModel.expenses.observe(viewLifecycleOwner) {
            adapter.setData(it)
            checkIfEmptyRecyclerView(it)
        }
        viewModel.total.observe(viewLifecycleOwner) {
            val total = "$it RSD"
            totalTextView.text = total
        }
    }

    private fun configureList() = with(binding) {
        expensesRecyclerView.adapter = adapter
        expensesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun checkIfEmptyRecyclerView(expenses: List<Expense>) = with(binding) {
        if (expenses.isEmpty()) {
            noItemsViewTextView.visibility = View.VISIBLE
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }

    @SuppressLint("InflateParams")
    private fun setupAddExpenseFAB() = with(binding) {
        addExpenseFloatingActionButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.add_expense_dialog, null)

            val addExpense = view.findViewById<Button>(R.id.addExpenseButton)
            val titleEditText = view.findViewById<EditText>(R.id.expenseTitleEditText)
            val valueEditText = view.findViewById<EditText>(R.id.expenseValueEditText)

            dialog.setContentView(view)
            dialog.show()

            addExpense.setOnClickListener {
                val title = titleEditText.text.toString()
                val value = valueEditText.text.toString()
                if (title.isEmpty() || value.isEmpty()) {
                    Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
                } else {
                    val newExpense = Expense(0, args.monthId, title, value.toInt())
                    viewModel.addExpense(newExpense)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupEditMonthFAB() = with(binding) {
        editMonthFloatingActionButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.edit_month_dialog, null)

            val monthNameEditText = view.findViewById<EditText>(R.id.monthNameEditText)
            val saveButton = view.findViewById<Button>(R.id.saveChangesButton)
            val deleteButton = view.findViewById<TextView>(R.id.deleteMonthButton)

            monthNameEditText.setText(args.monthName)

            dialog.setContentView(view)
            dialog.show()

            saveButton.setOnClickListener {
                if (monthNameEditText.text.isNullOrEmpty()) {
                    Toast.makeText(it.context, "Month name is required!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.updateMonth(
                        args.monthId,
                        monthNameEditText.text.toString(),
                        0
                    )
                    monthDetailsNameTextView.text = monthNameEditText.text.toString()
                    dialog.dismiss()
                }
            }

            deleteButton.setOnClickListener {
                viewModel.deleteMonth(args.monthId)
                dialog.dismiss()
                findNavController().navigate(R.id.action_expensesFragment_to_monthsFragment)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onExpenseClickListener(expense: Expense) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.expense_details_dialog, null)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val deleteButton = view.findViewById<TextView>(R.id.deleteButton)
        val expenseEditText = view.findViewById<EditText>(R.id.expenseNameDetailEditText)
        val valueEditText = view.findViewById<EditText>(R.id.expenseValueDetailEditText)
        val extraEditText = view.findViewById<EditText>(R.id.expenseExtraEditText)

        expenseEditText.setText(expense.title)
        valueEditText.setText(expense.price.toString())

        dialog.setContentView(view)
        dialog.show()

        saveButton.setOnClickListener {
            val title = expenseEditText.text.toString()
            val value = valueEditText.text.toString()
            val extra = extraEditText.text.toString()
            if (title.isNotEmpty() && value.isNotEmpty()) {
                if (extra.isEmpty()) {
                    val newExpense = Expense(
                        expense.id,
                        expense.monthId,
                        title,
                        value.toInt()
                    )
                    viewModel.updateExpense(newExpense)
                } else {
                    val total = value.toInt() + extra.toInt()
                    val newExpense = Expense(expense.id, expense.monthId, title, total)
                    viewModel.updateExpense(newExpense)
                }
                dialog.dismiss()
            } else {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            viewModel.deleteExpense(expense)
            dialog.dismiss()
        }
    }
}