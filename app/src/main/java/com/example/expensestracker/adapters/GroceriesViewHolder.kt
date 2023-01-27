package com.example.expensestracker.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.databinding.GroceryItemBinding
import com.example.expensestracker.domain.models.Grocery

class GroceriesViewHolder(private val binding: GroceryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(grocery: Grocery) = with(binding) {
        groceryNameTextView.text = grocery.name
    }
}