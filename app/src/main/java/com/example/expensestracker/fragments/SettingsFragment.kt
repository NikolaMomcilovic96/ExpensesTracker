package com.example.expensestracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.expensestracker.databinding.FragmentSettingsBinding
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val binding by viewBinding(FragmentSettingsBinding::inflate)
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: MonthsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureCurrencySpinner()
        configureDeleteButton()

        return binding.root
    }

    private fun configureDeleteButton() = with(binding) {
        deleteAllDataButton.setOnClickListener {
            viewModel.deleteAllData()
            Toast.makeText(it.context, "All data is deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configureCurrencySpinner() = with(binding) {
        sharedPreferences =
            context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        val spinner = currencySpinner
        val options = arrayOf("RSD", "USD", "EUR")
        spinner.adapter =
            ArrayAdapter(spinner.context, android.R.layout.simple_list_item_1, options)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                when (options[position]) {
                    "EUR" -> {
                        sharedPreferences.edit().putString("Currency", "EUR").apply()
                    }
                    "USD" -> {
                        sharedPreferences.edit().putString("Currency", "USD").apply()
                    }
                    else -> {
                        sharedPreferences.edit().putString("Currency", "RSD").apply()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }
    }
}