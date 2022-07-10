package com.example.expensestracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.models.Expense

class MonthDetailRecyclerViewAdapter(private val expenseClickListener: ExpenseClickListener) :
    RecyclerView.Adapter<MonthDetailRecyclerViewAdapter.ViewHolder>() {

    private var monthId = 0
    private var monthName = ""
    private var expenses = listOf<Expense>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.expense_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.itemView.findViewById<CardView>(R.id.expenseCardView)
        val title = holder.itemView.findViewById<TextView>(R.id.expenseTitleTextView)
        val value = holder.itemView.findViewById<TextView>(R.id.expenseValueTextView)
        title.text = expenses[position].title
        value.text = "${expenses[position].value} dinara"

        cardView.setOnClickListener {
            expenseClickListener.onExpenseClick(expenses[position], monthId, monthName)
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun setData(data: List<Expense>, monthId: Int, monthName: String) {
        this.expenses = data
        this.monthId = monthId
        this.monthName = monthName
    }

    interface ExpenseClickListener {
        fun onExpenseClick(expense: Expense, monthId: Int, monthName: String)
    }
}