package com.example.expensestracker.data.network.repository

import com.example.expensestracker.data.network.FirebaseService
import com.example.expensestracker.domain.models.Expense
import com.example.expensestracker.domain.models.Month
import com.example.expensestracker.domain.repository.MonthsRepository
import javax.inject.Inject

class MonthsRepositoryNetworkImpl @Inject constructor(private val firebaseService: FirebaseService) :
    MonthsRepository {
    override suspend fun getMonths(): List<Month> {
        return firebaseService.getYourMonths()
    }

    override suspend fun addMonth(month: Month) {
        val months = mutableListOf<Month>()
        val currentMonths = getMonths()
        for (m in currentMonths) {
            months.add(m)
        }
        months.add(month)
        firebaseService.backupMonths(months)
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
        firebaseService.deleteAllData()
    }

    override suspend fun backupData(months: List<Month>, expenses: List<Expense>) {
        firebaseService.backupMonths(months)
        firebaseService.backupExpenses(expenses)
    }

    override suspend fun getYourData(): Pair<List<Month>, List<Expense>> {
        val months = firebaseService.getYourMonths()
        val expenses = firebaseService.getYourExpenses()
        return Pair(months, expenses)
    }
}