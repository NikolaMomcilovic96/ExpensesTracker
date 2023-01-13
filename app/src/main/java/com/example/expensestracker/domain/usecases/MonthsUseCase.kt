package com.example.expensestracker.domain.usecases

import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month
import com.example.expensestracker.data.network.models.RestMonth
import com.example.expensestracker.di.qualifier.DB
import com.example.expensestracker.di.qualifier.Rest
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

    suspend fun backupData(data: List<RestMonth>) {
        restRepository.backupData(data)
    }

    suspend fun deleteData() {
        if (networkConnectionService.isOnline()) {
            restRepository.deleteAllData()
        }
        dbRepository.deleteAllData()
    }

    suspend fun getYourData(): List<RestMonth> {
        return restRepository.getYourData()
    }
}