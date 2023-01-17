package com.example.expensestracker.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.databinding.ExpenseItemBinding
import com.example.expensestracker.domain.models.Expense

class ExpensesViewHolder(
    private val binding: ExpenseItemBinding,
    private val expenseClickListener: ExpensesAdapter.ExpenseClickListener,
    private val currency: String
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(expense: Expense) = with(binding) {
        expenseTitleTextView.text = expense.title
        val price = "${expense.price} $currency"
        expenseValueTextView.text = price
        expenseCardView.setOnClickListener {
            expenseClickListener.onExpenseClickListener(expense)
        }
    }
}