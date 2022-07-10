package com.example.expensestracker.models

data class Month(
    val id: Int,
    val month: String,
    val expenses: List<Expense> = emptyList()
)
