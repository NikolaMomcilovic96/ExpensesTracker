package com.example.expensestracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.expensestracker.databinding.FragmentGroceriesBinding
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.GroceriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceriesFragment : Fragment() {

    private val binding by viewBinding(FragmentGroceriesBinding::inflate)
    private val viewModel: GroceriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.getAllGroceries()
        viewModel.groceries.observe(viewLifecycleOwner) {
            Log.d("GROCERIES", it.toString())
        }
    }
}