package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.R
import com.example.expensestracker.adapters.MonthsAdapter
import com.example.expensestracker.data.models.Month
import com.example.expensestracker.databinding.FragmentMonthsBinding
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthsFragment : Fragment() {

    private val binding by viewBinding(FragmentMonthsBinding::inflate)
    private val viewModel: MonthsViewModel by viewModels()
    private val adapter = MonthsAdapter {
        findNavController().navigate(
            MonthsFragmentDirections.actionMonthsFragmentToExpensesFragment(
                it.name,
                it.id
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureList()
        observeViewModel()
        setupFAB()

        return binding.root
    }

    private fun setupFAB() {
        binding.addMonthFloatingActionButton.setOnClickListener {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.add_month_dialog, null)
            val builder =
                AlertDialog.Builder(requireContext()).setView(dialogView)
                    .setTitle("Dodajte novi mesec").show()
            val button = dialogView.findViewById<Button>(R.id.createMonthButton)
            val editText = dialogView.findViewById<EditText>(R.id.monthNameEditText)

            button.setOnClickListener {
                val monthName = editText.text.toString()
                if (monthName.isNotEmpty()) {
                    val newMonth = Month(0, monthName, 0)
                    viewModel.addMonth(newMonth)
                    builder.dismiss()
                } else {
                    Toast.makeText(context, "Ime meseca je obavezno", Toast.LENGTH_SHORT)
                        .show()
                    editText.requestFocus()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.getMonths()
        viewModel.months.observe(viewLifecycleOwner) {
            adapter.setData(it)
            checkIfEmptyRecyclerView(it)
        }
    }

    private fun configureList() = with(binding) {
        monthsRecyclerView.adapter = adapter
        monthsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun checkIfEmptyRecyclerView(months: List<Month>) = with(binding) {
        if (months.isEmpty()) {
            noItemsViewTextView.visibility = View.VISIBLE
            val text = "Dodajte prvi mesec"
            noItemsViewTextView.text = text
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }
}