package com.example.expensestracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.ExpenseItemBinding

class ExpensesAdapter(
    private val expenseClickListener: ExpenseClickListener
) : RecyclerView.Adapter<ExpensesViewHolder>() {

    private var expenses = mutableListOf<Expense>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        return ExpensesViewHolder(
            binding = ExpenseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            expenseClickListener = expenseClickListener
        )
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun setData(data: List<Expense>) {
        this.expenses = data.toMutableList()
        notifyDataSetChanged()
    }

    interface ExpenseClickListener {
        fun onExpenseClickListener(expense: Expense)
    }
}