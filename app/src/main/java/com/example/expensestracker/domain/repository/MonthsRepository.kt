package com.example.expensestracker.domain.repository

import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month
import com.example.expensestracker.data.network.models.RestMonth

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

    suspend fun backupData(data: List<RestMonth>)

    suspend fun getYourData(): List<RestMonth>
}