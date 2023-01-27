package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensestracker.adapters.GroceriesAdapter
import com.example.expensestracker.databinding.FragmentGroceriesBinding
import com.example.expensestracker.dialogs.AddGroceryDialog
import com.example.expensestracker.domain.models.Grocery
import com.example.expensestracker.fragments.delegates.viewBinding
import com.example.expensestracker.viewmodel.GroceriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceriesFragment : Fragment() {

    private val binding by viewBinding(FragmentGroceriesBinding::inflate)
    private val viewModel: GroceriesViewModel by viewModels()
    private val adapter = GroceriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        configureList()
        observeViewModel()
        configureFAB()

        return binding.root
    }

    private fun configureFAB() = with(binding) {
        addGroceryFloatingActionButton.setOnClickListener {
            val dialog = AddGroceryDialog(it.context, viewModel)
            dialog.showDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.getAllGroceries()
        viewModel.groceries.observe(viewLifecycleOwner) {
            checkIfEmptyRecyclerView(it)
            adapter.setData(it)
        }
    }

    private fun configureList() = with(binding) {
        groceriesRecyclerView.adapter = adapter
        groceriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun checkIfEmptyRecyclerView(groceries: List<Grocery>) = with(binding) {
        if (groceries.isEmpty()) {
            noItemsViewTextView.visibility = View.VISIBLE
            val text = "Add first grocery"
            noItemsViewTextView.text = text
        } else {
            noItemsViewTextView.visibility = View.GONE
        }
    }
}