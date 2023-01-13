package com.example.expensestracker.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.expensestracker.di.qualifier.DB
import com.example.expensestracker.domain.models.Expense
import com.example.expensestracker.domain.models.Month
import com.example.expensestracker.domain.repository.MonthsRepository
import com.example.expensestracker.domain.usecases.MonthsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthsViewModel @Inject constructor(
    @DB private val dbRepository: MonthsRepository,
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

    fun backupData(context: Context) {
        viewModelScope.launch {
            val months = monthsUseCase.getMonths()
            val expenses = mutableListOf<Expense>()
            for (m in months) {
                val monthlyExpenses = monthsUseCase.getExpenses(m.id)
                for (e in monthlyExpenses) {
                    expenses.add(e)
                }
            }
            monthsUseCase.testBackup(months, expenses, context)
        }
    }

    fun getYourData(context: Context) {
        viewModelScope.launch {
            val data = monthsUseCase.getYourData(context)
            for (m in data.first) {
                val month = Month(m.id, m.name, m.total)
                dbRepository.addMonth(month)
                getMonths()
            }
            for (e in data.second) {
                val expense = Expense(e.id, e.monthId, e.title, e.price)
                dbRepository.addExpense(expense)
                getExpenses(e.monthId)
            }
        }
    }
}