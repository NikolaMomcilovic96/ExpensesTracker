package com.example.expensestracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.models.Month

class MonthsRecyclerViewAdapter(private val monthClickedInterface: MonthClickedInterface) :
    RecyclerView.Adapter<MonthsRecyclerViewAdapter.ViewHolder>() {

    private var months = listOf<Month>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.month_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = holder.itemView.findViewById<CardView>(R.id.monthCardView)
        val month = holder.itemView.findViewById<TextView>(R.id.monthNameTextView)
        val total = holder.itemView.findViewById<TextView>(R.id.totalExpensesTextView)
        var totalExpenses = 0
        for (i in months[position].expenses) {
            totalExpenses += i.value
        }
        month.text = months[position].month
        total.text = "$totalExpenses dinara"
        card.setOnClickListener {
            monthClickedInterface.onMonthClick(months[position])
        }
    }

    override fun getItemCount(): Int {
        return months.size
    }

    fun setData(months: List<Month>) {
        this.months = months
    }

    interface MonthClickedInterface {
        fun onMonthClick(month: Month)
    }
}