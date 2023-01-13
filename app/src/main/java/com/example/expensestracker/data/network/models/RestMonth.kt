package com.example.expensestracker.data.network.models

import com.example.expensestracker.data.database.models.Expense

data class RestMonth(
    val id: Int,
    val name: String,
    val total: Int,
    val expenses: List<Expense>? = null
)
