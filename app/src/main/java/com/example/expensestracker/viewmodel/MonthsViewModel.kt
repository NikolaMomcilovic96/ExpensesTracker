package com.example.expensestracker.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month
import com.example.expensestracker.data.network.models.RestMonth
import com.example.expensestracker.di.qualifier.DB
import com.example.expensestracker.domain.mappers.network.RestMonthMapper
import com.example.expensestracker.domain.repository.MonthsRepository
import com.example.expensestracker.domain.services.NetworkConnectionService
import com.example.expensestracker.domain.usecases.MonthsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthsViewModel @Inject constructor(
    @DB private val dbRepository: MonthsRepository,
    private val networkConnectionService: NetworkConnectionService,
    private val restMonthMapper: RestMonthMapper,
    private val monthsUseCase: MonthsUseCase
) : ViewModel() {

    private val _months: MutableLiveData<List<Month>> = MutableLiveData()
    val months: LiveData<List<Month>>
        get() = _months

    private val _expenses: MutableLiveData<List<Expense>> = MutableLiveData()
    val expenses: LiveData<List<Expense>>
        get() = _expenses

    val total: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(expenses) { result ->
            val totalCost = result.sumOf { it.price }
            postValue(totalCost)
        }
    }

    fun getMonths() {
        viewModelScope.launch {
            val result = dbRepository.getMonths()
            _months.postValue(result)
        }
    }

    fun addMonth(month: Month) {
        viewModelScope.launch {
            dbRepository.addMonth(month)
            getMonths()
        }
    }

    fun deleteMonth(monthId: Int) {
        viewModelScope.launch {
            dbRepository.deleteMonth(monthId)
            getMonths()
        }
    }

    fun updateMonth(monthId: Int, name: String, total: Int) {
        viewModelScope.launch {
            dbRepository.updateMonth(monthId, name, total)
        }
    }

    fun getExpenses(monthId: Int) {
        viewModelScope.launch {
            val result = dbRepository.getExpenses(monthId)
            _expenses.postValue(result)
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            dbRepository.addExpense(expense)
            getExpenses(expense.monthId)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            dbRepository.deleteExpense(expense.id)
            getExpenses(expense.monthId)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            dbRepository.updateExpense(expense.id, expense.title, expense.price)
            getExpenses(expense.monthId)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch {
            monthsUseCase.deleteData()
        }
    }

    fun syncAllData(context: Context) {
        viewModelScope.launch {
            if (networkConnectionService.isOnline()) {
                val data = mutableListOf<RestMonth>()
                val months = monthsUseCase.getMonths()
                for (m in months) {
                    val monthlyExpenses = monthsUseCase.getExpenses(m.id)
                    val month = restMonthMapper.map(m, monthlyExpenses)
                    data.add(month)
                }
                monthsUseCase.backupData(data)
                syncedToast(context)
            } else {
                internetRequiredToast(context)
            }
        }
    }

    fun getYourData(context: Context) {
        viewModelScope.launch {
            if (networkConnectionService.isOnline()) {
                val data = monthsUseCase.getYourData()
                for (d in data) {
                    val month = Month(d.id, d.name, d.total)
                    dbRepository.addMonth(month)
                    getMonths()
                    if (d.expenses?.isEmpty() == false) {
                        for (e in d.expenses) {
                            val expense = Expense(e.id, e.monthId, e.title, e.price)
                            dbRepository.addExpense(expense)
                            getExpenses(d.id)
                        }
                    }
                }
                syncedToast(context)
            } else {
                internetRequiredToast(context)
            }
        }
    }

    private fun syncedToast(context: Context) {
        Toast.makeText(context, "All data is synced", Toast.LENGTH_SHORT).show()
    }

    private fun internetRequiredToast(context: Context) {
        Toast.makeText(context, "Internet connection is required", Toast.LENGTH_SHORT).show()
    }
}