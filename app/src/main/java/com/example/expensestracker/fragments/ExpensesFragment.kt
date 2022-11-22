package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        setupFAB()

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
            val total = "$it dinara"
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
            val text = "Dodajte prvi trosak"
            noItemsViewTextView.text = text
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }

    private fun setupFAB() {
        binding.addExpenseFloatingActionButton.setOnClickListener {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.add_expense_dialog, null)
            val builder =
                AlertDialog.Builder(requireContext()).setView(dialogView)
                    .setTitle("Dodajte novi trosak").show()
            val addExpense = dialogView.findViewById<Button>(R.id.addExpenseButton)
            val titleEditText = dialogView.findViewById<EditText>(R.id.expenseTitleEditText)
            val valueEditText = dialogView.findViewById<EditText>(R.id.expenseValueEditText)
            addExpense.setOnClickListener {
                val title = titleEditText.text.toString()
                val value = valueEditText.text.toString()
                if (title.isEmpty() && value.isEmpty()) {
                    Toast.makeText(context, "Sva polja su obavezna", Toast.LENGTH_SHORT).show()
                } else {
                    val newExpense = Expense(0, args.monthId, title, value.toInt())
                    viewModel.addExpense(newExpense)
                    builder.dismiss()
                }
            }
        }
    }

    override fun onExpenseClickListener(expense: Expense) {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.expense_details_dialog, null)
        val builder = AlertDialog.Builder(requireContext()).setView(dialogView)
            .setTitle("Izmenite postojeci trosak").show()
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
        val deleteButton = dialogView.findViewById<ImageView>(R.id.deleteButton)
        val expenseEditText = dialogView.findViewById<EditText>(R.id.expenseNameDetailEditText)
        val valueEditText = dialogView.findViewById<EditText>(R.id.expenseValueDetailEditText)
        val extraEditText = dialogView.findViewById<EditText>(R.id.expenseExtraEditText)

        expenseEditText.setText(expense.title)
        valueEditText.setText(expense.price.toString())

        saveButton.setOnClickListener {
            val title = expenseEditText.text.toString()
            val value = valueEditText.text.toString()
            val extra = extraEditText.text.toString()
            if (title.isNotEmpty() && value.isNotEmpty()) {
                if (extra.isEmpty()) {
                    viewModel.updateExpense(expense)
                } else {
                    val total = value.toInt() + extra.toInt()
                    val updatedExpense = Expense(expense.id, expense.monthId, title, total)
                    viewModel.updateExpense(updatedExpense)
                }
                builder.dismiss()
            }
        }

        deleteButton.setOnClickListener {
            viewModel.deleteExpense(expense)
            builder.dismiss()
        }
    }
}