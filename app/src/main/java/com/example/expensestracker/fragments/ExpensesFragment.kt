package com.example.expensestracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.adapters.ExpensesAdapter
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.FragmentExpensesBinding
import com.example.expensestracker.dialogs.AddExpenseDialog
import com.example.expensestracker.dialogs.EditExpenseDialog
import com.example.expensestracker.dialogs.EditMonthDialog
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpensesFragment : Fragment(), ExpensesAdapter.ExpenseClickListener {

    private val binding by viewBinding(FragmentExpensesBinding::inflate)
    private val viewModel: MonthsViewModel by viewModels()
    private val adapter = ExpensesAdapter(this)
    private val args by navArgs<ExpensesFragmentArgs>()
    private lateinit var sharedPreferences: SharedPreferences

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
        sharedPreferences =
            context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        val currency = sharedPreferences.getString("Currency", "RSD").toString()
        viewModel.getExpenses(args.monthId)
        viewModel.expenses.observe(viewLifecycleOwner) {
            adapter.setData(it, currency)
            checkIfEmptyRecyclerView(it)
        }
        viewModel.total.observe(viewLifecycleOwner) {
            val total = "$it $currency"
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

    private fun setupAddExpenseFAB() = with(binding) {
        addExpenseFloatingActionButton.setOnClickListener {
            val addExpenseDialog = AddExpenseDialog(it.context, viewModel, args.monthId)
            addExpenseDialog.showDialog()
        }
    }

    private fun setupEditMonthFAB() = with(binding) {
        editMonthFloatingActionButton.setOnClickListener {
            val editMonthDialog = EditMonthDialog(
                it.context,
                viewModel,
                args.monthName,
                args.monthId,
                args.total,
                monthDetailsNameTextView,
                ExpensesFragment()
            )
            editMonthDialog.showDialog()
        }
    }

    override fun onExpenseClickListener(expense: Expense) {
        val editExpenseDialog = EditExpenseDialog(requireContext(), viewModel, expense)
        editExpenseDialog.showDialog()
    }
}