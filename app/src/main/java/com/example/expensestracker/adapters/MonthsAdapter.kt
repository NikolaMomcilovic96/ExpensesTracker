package com.example.expensestracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.models.Month
import com.example.expensestracker.databinding.MonthItemBinding

class MonthsAdapter(
    private val onUserClicked: (Month) -> Unit
) :
    RecyclerView.Adapter<MonthsViewHolder>() {

    private var months = mutableListOf<Month>()
    private var currency = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthsViewHolder {
        return MonthsViewHolder(
            binding = MonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onUserClicked = onUserClicked,
            currency = currency
        )
    }

    override fun onBindViewHolder(holder: MonthsViewHolder, position: Int) {
        holder.bind(months[position])
    }

    override fun getItemCount(): Int {
        return months.size
    }

    fun setData(months: List<Month>, currency: String) {
        this.months = months.toMutableList()
        this.currency = currency
        notifyDataSetChanged()
    }
}