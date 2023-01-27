package com.example.expensestracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.databinding.GroceryItemBinding
import com.example.expensestracker.domain.models.Grocery

class GroceriesAdapter :
    RecyclerView.Adapter<GroceriesViewHolder>() {

    private var groceries = mutableListOf<Grocery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceriesViewHolder {
        return GroceriesViewHolder(
            binding = GroceryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: GroceriesViewHolder, position: Int) {
        holder.bind(groceries[position])
    }

    override fun getItemCount(): Int {
        return groceries.size
    }

    fun setData(groceries: List<Grocery>) {
        this.groceries = groceries.toMutableList()
        notifyDataSetChanged()
    }
}