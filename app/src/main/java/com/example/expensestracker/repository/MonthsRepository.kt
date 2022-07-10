package com.example.expensestracker.repository

import com.example.expensestracker.models.Expense
import com.example.expensestracker.models.Month
import com.example.expensestracker.services.FirebaseService

class MonthsRepository(private val service: FirebaseService) {

    suspend fun getMonths() = service.getMonths()

    suspend fun updateMonths(months: List<Month>) = service.updateMonths(months)

    suspend fun getMonth(id: Int) = service.getMonth(id)


    suspend fun updateExpenses(id: Int, month: Month) =
        service.updateExpenses(id, month)

    suspend fun getExpenses(id: Int) = service.getExpenses(id)

    suspend fun updateExpense(monthId: Int, expenseId: Int, expense: Expense) =
        service.updateExpense(monthId, expenseId, expense)

    suspend fun deleteExpense(monthId: Int, expenseId: Int) = service.deleteExpense(monthId, expenseId)
}