package com.example.expensestracker.domain.mappers.network

import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month
import com.example.expensestracker.data.network.models.RestMonth
import com.example.expensestracker.domain.mappers.RestMapper

class RestMonthMapper : RestMapper<Month, List<Expense>, RestMonth> {
    override fun map(month: Month, expenses: List<Expense>): RestMonth {
        return RestMonth(
            id = month.id ,
            name = month.name,
            total = month.total,
            expenses = expenses
        )
    }
}