package com.example.expensestracker.data.network.repository

import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month
import com.example.expensestracker.data.network.FirebaseService
import com.example.expensestracker.data.network.models.RestMonth
import com.example.expensestracker.domain.repository.MonthsRepository
import javax.inject.Inject

class MonthsRepositoryNetworkImpl @Inject constructor(private val firebaseService: FirebaseService) :
    MonthsRepository {
    override suspend fun getMonths(): List<Month> {
        throw UnsupportedOperationException()
    }

    override suspend fun addMonth(month: Month) {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteMonth(monthId: Int) {
        throw UnsupportedOperationException()
    }

    override suspend fun updateMonth(monthId: Int, name: String, total: Int) {
        throw UnsupportedOperationException()
    }

    override suspend fun getExpenses(monthId: Int): List<Expense> {
        throw UnsupportedOperationException()
    }

    override suspend fun addExpense(expense: Expense) {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteExpense(expenseId: Int) {
        throw UnsupportedOperationException()
    }

    override suspend fun updateExpense(expenseId: Int, title: String, price: Int) {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteAllData() {
        firebaseService.deleteAllData(emptyList())
    }

    override suspend fun backupData(data: List<RestMonth>) {
        firebaseService.backupData(data)
    }

    override suspend fun getYourData(): List<RestMonth> {
        return firebaseService.getYourData()
    }
}