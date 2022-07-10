package com.example.expensestracker.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.Constants
import com.example.expensestracker.R
import com.example.expensestracker.adapters.MonthDetailRecyclerViewAdapter
import com.example.expensestracker.databinding.FragmentMonthDetailsBinding
import com.example.expensestracker.models.Expense
import com.example.expensestracker.models.Month
import com.example.expensestracker.repository.MonthsRepository
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.example.expensestracker.services.FirebaseService

class MonthDetailsFragment : Fragment(), MonthDetailRecyclerViewAdapter.ExpenseClickListener {

    private lateinit var binding: FragmentMonthDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var service: FirebaseService
    private lateinit var repository: MonthsRepository
    private lateinit var viewModel: MonthsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthDetailsBinding.inflate(layoutInflater)
        sharedPreferences =
            activity?.getSharedPreferences(Constants.sharedPref, Context.MODE_PRIVATE)!!

        showProgressBar()

        val monthId = sharedPreferences.getInt(Constants.Id, -1)
        val monthName = sharedPreferences.getString(Constants.MonthName, "").toString()
        val expenses = mutableListOf<Expense>()

        setViewModel()
        var countTotal = 0

        viewModel.getMonth(monthId)
        viewModel.month.observe(viewLifecycleOwner) {
            binding.monthDetailsNameTextView.text = it.month
            for (i in it.expenses) {
                countTotal += i.value
            }
            binding.totalTextView.text = "$countTotal dinara"
            refreshRecyclerView(it.expenses, monthId, monthName)
            hideProgressBar()
            for (i in it.expenses) {
                expenses.add(i)
            }
        }

        binding.addExpenseFloatingActionButton.setOnClickListener {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.add_expense_dialog, null)
            val builder =
                AlertDialog.Builder(context).setView(dialogView)
                    .setTitle("Dodaj novi trosak").show()
            val button = dialogView.findViewById<Button>(R.id.addExpenseButton)
            val titleEditText = dialogView.findViewById<EditText>(R.id.expenseTitleEditText)
            val valueEditText = dialogView.findViewById<EditText>(R.id.expenseValueEditText)
            button.setOnClickListener {
                val title = titleEditText.text.toString()
                val value = valueEditText.text.toString()
                if (title.isEmpty() && value.isEmpty()) {
                    Toast.makeText(context, "Sva polja su obavezna", Toast.LENGTH_SHORT).show()
                } else {
                    val expenseId = expenses.size
                    val newExpense = Expense(expenseId, title, value.toInt())
                    expenses.add(newExpense)
                    Log.d("TROSKOVI", expenses.toString())
                    val month = Month(monthId, monthName, expenses)
                    viewModel.updateExpenses(monthId, month)
                    refreshRecyclerView(expenses, monthId, monthName)
                    var total = 0
                    for (i in month.expenses) {
                        total += i.value
                    }
                    updateTotal(total)
                    builder.dismiss()
                }
            }
        }

        return binding.root
    }

    private fun refreshRecyclerView(
        expenses: List<Expense>,
        monthId: Int,
        monthName: String,
    ) {
        val adapter = MonthDetailRecyclerViewAdapter(this)
        binding.expensesRecyclerView.adapter = adapter
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setData(expenses, monthId, monthName)
    }

    private fun setViewModel() {
        service = FirebaseService.instance
        repository = MonthsRepository(service)
        viewModel = MonthsViewModel(repository)
    }

    private fun showProgressBar() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar2.visibility = View.GONE
    }

    private fun updateTotal(total: Int) {
        binding.totalTextView.text = "$total dinara"
    }

    override fun onExpenseClick(expense: Expense, monthId: Int, monthName: String) {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.expense_details_dialog, null)
        val builder =
            AlertDialog.Builder(context).setView(dialogView)
                .setTitle("Izmenite postojeci trosak").show()
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
        val deleteButton = dialogView.findViewById<ImageView>(R.id.deleteButton)
        val expenseTextView = dialogView.findViewById<TextView>(R.id.expenseNameDetailTextView)
        val valueTextView = dialogView.findViewById<TextView>(R.id.expeneValueDetailTextView)
        val expenseEditText = dialogView.findViewById<EditText>(R.id.expenseNameDetailEditText)
        val valueEditText = dialogView.findViewById<EditText>(R.id.expenseValueDetailEditText)

        expenseTextView.text = expense.title
        valueTextView.text = "${expense.value} dinara"

        val expenses = mutableListOf<Expense>()
        viewModel.getExpenses(monthId)
        viewModel.expenses.observe(viewLifecycleOwner) {
            for (i in it) {
                expenses.add(i)
            }
        }

        saveButton.setOnClickListener {
            val title = expenseEditText.text.toString()
            val value = valueEditText.text.toString()
            val expenseId = expense.id
            if (title.isNotEmpty() && value.isNotEmpty()) {
                expenses.removeAt(expenseId)
                val newExpense = Expense(expenseId, title, value.toInt())
                viewModel.updateExpense(monthId, expenseId, newExpense)
                expenses.add(newExpense)
                var newTotal = 0
                for (i in expenses) {
                    newTotal += i.value
                }
                updateRV(monthId, monthName)
                builder.dismiss()
            }
        }

        deleteButton.setOnClickListener {
            val expenseId = expense.id
            viewModel.deleteExpense(monthId, expenseId)
            updateRV(monthId, monthName)
            builder.dismiss()
        }
    }

    private fun updateRV(monthId: Int, monthName: String) {
        viewModel.getExpenses(monthId)
        viewModel.expenses.observe(viewLifecycleOwner) {
            val adapter = MonthDetailRecyclerViewAdapter(this)
            binding.expensesRecyclerView.adapter = adapter
            binding.expensesRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter.setData(it, monthId, monthName)
            var total = 0
            for (i in it) {
                total += i.value
            }
            binding.totalTextView.text = "$total dinara"
        }
    }
}