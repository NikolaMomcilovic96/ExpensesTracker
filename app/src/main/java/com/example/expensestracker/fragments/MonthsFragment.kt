package com.example.expensestracker.fragments

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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.R
import com.example.expensestracker.adapters.MonthsAdapter
import com.example.expensestracker.data.models.Month
import com.example.expensestracker.databinding.FragmentMonthsBinding
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthsFragment : Fragment() {

    private val binding by viewBinding(FragmentMonthsBinding::inflate)
    private val viewModel: MonthsViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val adapter = MonthsAdapter {
        findNavController().navigate(
            MonthsFragmentDirections.actionMonthsFragmentToExpensesFragment(
                it.name,
                it.id,
                it.total
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
        configureSettingsButton()

        return binding.root
    }

    private fun setupFAB() {
        binding.addMonthFloatingActionButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.add_month_dialog, null)
            val button = view.findViewById<Button>(R.id.createMonthButton)
            val editText = view.findViewById<EditText>(R.id.monthNameEditText)

            dialog.setContentView(view)
            dialog.show()

            button.setOnClickListener {
                val monthName = editText.text.toString()
                if (monthName.isNotEmpty()) {
                    val newMonth = Month(0, monthName, 0)
                    viewModel.addMonth(newMonth)
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Month name is required!", Toast.LENGTH_SHORT)
                        .show()
                    editText.requestFocus()
                }
            }
        }
    }

    private fun observeViewModel() {
        sharedPreferences =
            context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        val currency = sharedPreferences.getString("Currency", "RSD").toString()
        viewModel.getMonths()
        viewModel.months.observe(viewLifecycleOwner) {
            adapter.setData(it, currency)
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
            val text = "Add first month"
            noItemsViewTextView.text = text
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }

    private fun configureSettingsButton() = with(binding) {
        settingsButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_monthsFragment_to_settingsFragment)
        }
    }
}