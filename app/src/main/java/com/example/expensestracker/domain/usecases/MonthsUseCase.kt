package com.example.expensestracker.domain.usecases

import android.content.Context
import android.widget.Toast
import com.example.expensestracker.di.qualifier.DB
import com.example.expensestracker.di.qualifier.Rest
import com.example.expensestracker.domain.models.Expense
import com.example.expensestracker.domain.models.Month
import com.example.expensestracker.domain.repository.MonthsRepository
import com.example.expensestracker.domain.services.NetworkConnectionService
import javax.inject.Inject

class MonthsUseCase @Inject constructor(
    @DB private val dbRepository: MonthsRepository,
    @Rest private val restRepository: MonthsRepository,
    private val networkConnectionService: NetworkConnectionService
) {

    suspend fun getMonths(): List<Month> {
        return dbRepository.getMonths()
    }

    suspend fun getExpenses(monthId: Int): List<Expense> {
        return dbRepository.getExpenses(monthId)
    }

    suspend fun deleteData() {
        if (networkConnectionService.isOnline()) {
            restRepository.deleteAllData()
        }
        dbRepository.deleteAllData()
    }

    suspend fun getYourData(context: Context): Pair<List<Month>, List<Expense>> {
        val months = mutableListOf<Month>()
        val expenses = mutableListOf<Expense>()
        if (networkConnectionService.isOnline()) {
            for (m in restRepository.getYourData().first) {
                months.add(m)
            }
            for (e in restRepository.getYourData().second) {
                expenses.add(e)
            }
            syncedToast(context)
        } else {
            noData(context)
        }
        return Pair(months, expenses)
    }

    suspend fun testBackup(months: List<Month>, expenses: List<Expense>, context: Context) {
        if (networkConnectionService.isOnline()) {
            restRepository.backupData(months, expenses)
            syncedToast(context)
        } else {
            internetRequiredToast(context)
        }
    }

    private fun syncedToast(context: Context) {
        Toast.makeText(context, "All data is synced", Toast.LENGTH_SHORT).show()
    }

    private fun internetRequiredToast(context: Context) {
        Toast.makeText(context, "Internet connection is required", Toast.LENGTH_SHORT).show()
    }

    private fun noData(context: Context) {
        Toast.makeText(context, "No data on server", Toast.LENGTH_SHORT).show()
    }
}