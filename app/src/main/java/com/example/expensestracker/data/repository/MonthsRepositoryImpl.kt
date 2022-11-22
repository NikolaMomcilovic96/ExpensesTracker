package com.example.expensestracker.data.repository

import com.example.expensestracker.data.database.MonthsDao
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.data.models.Month
import com.example.expensestracker.domain.repository.MonthsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MonthsRepositoryImpl @Inject constructor(private val monthsDao: MonthsDao) :
    MonthsRepository {
    override suspend fun getMonths(): List<Month> = withContext(Dispatchers.IO) {
        return@withContext monthsDao.getMonths()
    }

    override suspend fun addMonth(month: Month) = withContext(Dispatchers.IO) {
        monthsDao.addMonth(month)
    }

    override suspend fun deleteMonth(monthId: Int) {
        monthsDao.deleteMonth(monthId)
    }

    override suspend fun updateMonth(monthId: Int, name: String, total: Int) {
        monthsDao.updateMonth(monthId, name, total)
    }

    override suspend fun getExpenses(monthId: Int): List<Expense> = withContext(Dispatchers.IO) {
        return@withContext monthsDao.getExpenses(monthId)
    }

    override suspend fun addExpense(expense: Expense) {
        monthsDao.addExpense(expense)
    }

    override suspend fun deleteExpense(expenseId: Int) {
        monthsDao.deleteExpense(expenseId)
    }

    override suspend fun updateExpense(expenseId: Int, title: String, price: Int) {
        monthsDao.updateExpense(expenseId, title, price)
    }
}