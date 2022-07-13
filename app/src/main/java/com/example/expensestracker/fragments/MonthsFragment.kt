package com.example.expensestracker.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.Constants
import com.example.expensestracker.R
import com.example.expensestracker.adapters.MonthsRecyclerViewAdapter
import com.example.expensestracker.databinding.FragmentMonthsBinding
import com.example.expensestracker.models.Expense
import com.example.expensestracker.models.Month
import com.example.expensestracker.repository.MonthsRepository
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.example.expensestracker.services.FirebaseService

class MonthsFragment : Fragment(), MonthsRecyclerViewAdapter.MonthClickedInterface {

    private lateinit var binding: FragmentMonthsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var service: FirebaseService
    private lateinit var repository: MonthsRepository
    private lateinit var viewModel: MonthsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthsBinding.inflate(layoutInflater)

        showProgressBar()

        (activity as AppCompatActivity).supportActionBar?.hide()
        sharedPreferences =
            activity?.getSharedPreferences(Constants.sharedPref, Context.MODE_PRIVATE)!!

        setViewModel()
        val months = mutableListOf<Month>()

        viewModel.getMonths()
        viewModel.months.observe(viewLifecycleOwner) {
            val adapter = MonthsRecyclerViewAdapter(this)
            binding.monthsRecyclerView.adapter = adapter
            binding.monthsRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter.setData(it)
            for (i in it) {
                months.add(i)
            }
        }
        hideProgressBar()

        binding.addMonthFloatingActionButton.setOnClickListener {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.add_month_dialog, null)
            val builder =
                AlertDialog.Builder(context).setView(dialogView)
                    .setTitle("Dodajte novi mesec").show()
            val button = dialogView.findViewById<Button>(R.id.createMonthButton)
            val editText = dialogView.findViewById<EditText>(R.id.monthNameEditText)

            val usualExpenses = listOf(
                Expense(0, Constants.teretana, 3600)
            )

            button.setOnClickListener {
                val monthName = editText.text.toString()
                val id = months.size
                if (monthName.isNotEmpty()) {
                    val newMonth = Month(id, monthName, usualExpenses)
                    months.add(newMonth)
                    viewModel.updateMonths(months)
                    refreshRecyclerView(months)
                    builder.dismiss()
                } else {
                    Toast.makeText(context, "Ime meseca je obavezno", Toast.LENGTH_SHORT)
                        .show()
                    editText.requestFocus()
                }
            }
        }

        return binding.root
    }

    private fun refreshRecyclerView(data: List<Month>) {
        val adapter = MonthsRecyclerViewAdapter(this)
        binding.monthsRecyclerView.adapter = adapter
        binding.monthsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setData(data)
    }

    override fun onMonthClick(month: Month) {
        sharedPreferences.edit().apply {
            putInt(Constants.Id, month.id)
            putString(Constants.MonthName, month.month)
        }.apply()
        Navigation.findNavController(requireView())
            .navigate(R.id.action_monthsFragment_to_monthDetailsFragment)
    }

    private fun setViewModel() {
        service = FirebaseService.instance
        repository = MonthsRepository(service)
        viewModel = MonthsViewModel(repository)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}