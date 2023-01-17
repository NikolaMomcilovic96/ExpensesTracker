package com.example.expensestracker.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.databinding.MonthItemBinding
import com.example.expensestracker.domain.models.Month

class MonthsViewHolder(
    private val binding: MonthItemBinding,
    private val onUserClicked: (Month) -> Unit,
    private val currency: String
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(month: Month) = with(binding) {
        monthNameTextView.text = month.name
        val totalPrice = "${month.total} $currency"
        totalExpensesTextView.text = totalPrice

        monthCardView.setOnClickListener {
            onUserClicked(month)
        }
    }
}