package com.example.expensestracker.domain.repository

import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.data.models.Month


interface MonthsRepository {
    suspend fun getMonths(): List<Month>

    suspend fun addMonth(month: Month)

    suspend fun deleteMonth(monthId: Int)

    suspend fun updateMonth(monthId: Int, name: String, total: Int)

    suspend fun getExpenses(monthId: Int): List<Expense>

    suspend fun addExpense(expense: Expense)

    suspend fun deleteExpense(expenseId: Int)

    suspend fun updateExpense(expenseId: Int, title: String, price: Int)

    suspend fun deleteAllData()
}