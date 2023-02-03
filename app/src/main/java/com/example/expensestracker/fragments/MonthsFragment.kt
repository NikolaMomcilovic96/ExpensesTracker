package com.example.expensestracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.adapters.MonthsAdapter
import com.example.expensestracker.databinding.FragmentMonthsBinding
import com.example.expensestracker.dialogs.AddMonthDialog
import com.example.expensestracker.domain.models.Month
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

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
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences =
            context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        val isAuthorized = sharedPreferences.getBoolean("isAuthorized", false)
        if (isAuthorized) {
            displayData()
        } else {
            authUser()
        }

        return binding.root
    }

    private fun displayData() {
        configureList()
        observeViewModel()
        autoBackupData()
        setupFAB()
    }

    private fun setupFAB() {
        binding.addMonthFloatingActionButton.setOnClickListener {
            val addMonthDialog = AddMonthDialog(it.context, viewModel)
            addMonthDialog.showDialog()
        }
    }

    private fun observeViewModel() {
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
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }

    private fun autoBackupData() {
        val autoBackup = sharedPreferences.getBoolean("autoBackup", false)
        if (autoBackup) {
            viewModel.backupData(requireContext())
        }
    }

    private fun authUser() {
        executor = ContextCompat.getMainExecutor(requireActivity())
        biometricPrompt = androidx.biometric.BiometricPrompt(
            this,
            executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    displayData()
                    sharedPreferences.edit().putBoolean("isAuthorized", true).apply()
                }
            })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}