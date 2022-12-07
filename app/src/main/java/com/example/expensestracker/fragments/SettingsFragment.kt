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
import androidx.navigation.fragment.findNavController
import com.example.expensestracker.databinding.FragmentSettingsBinding
import com.example.expensestracker.fragments.delegates.viewBinding

class SettingsFragment : Fragment() {

    private val binding by viewBinding(FragmentSettingsBinding::inflate)
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureBackButton()
        configureLanguageSwitch()
        configureCurrencySpinner()

        return binding.root
    }

    private fun configureCurrencySpinner() = with(binding) {
        sharedPreferences =
            context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        val spinner = spinner
        val options = arrayOf("EUR", "USD", "RSD")
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

    private fun configureLanguageSwitch() = with(binding) {
        languageSwitch.setOnClickListener {
            if (languageSwitch.isChecked) {
                TODO("Nzm kako")
            } else {
                TODO("Nzm kako")
            }
        }
    }

    private fun configureBackButton() = with(binding) {
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}