package com.example.expensestracker.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expensestracker.R
import com.example.expensestracker.databinding.GroceryItemBinding
import com.example.expensestracker.domain.models.Grocery

class GroceriesViewHolder(
    private val binding: GroceryItemBinding,
    private val groceryClickListener: GroceriesAdapter.GroceryClickListener,
    private val groceryCheckboxClickListener: GroceriesAdapter.GroceryCheckboxClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(grocery: Grocery) = with(binding) {
        groceryNameTextView.text = grocery.name

        var isChecked = grocery.checked
        Glide.with(groceryCheckBox.context).load(loadImage(isChecked)).into(groceryCheckBox)

        groceryCheckBox.setOnClickListener {
            isChecked = !isChecked
            Glide.with(groceryCheckBox.context).load(loadImage(isChecked)).into(groceryCheckBox)
            groceryCheckboxClickListener.onGroceryCheckboxClickListener(grocery)
        }

        groceryCardView.setOnClickListener {
            groceryClickListener.onGroceryClickListener(grocery)
        }
    }

    private fun loadImage(boolean: Boolean): Any {
        return if (boolean) {
            R.drawable.checked
        } else {
            R.drawable.unchecked
        }
    }
}