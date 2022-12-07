package com.example.expensestracker.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.ExpenseItemBinding

class ExpensesViewHolder(
    private val binding: ExpenseItemBinding,
    private val expenseClickListener: ExpensesAdapter.ExpenseClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(expense: Expense) = with(binding) {
        expenseTitleTextView.text = expense.title
        val price = "${expense.price} RSD"
        expenseValueTextView.text = price
        expenseCardView.setOnClickListener {
            expenseClickListener.onExpenseClickListener(expense)
        }
    }
}