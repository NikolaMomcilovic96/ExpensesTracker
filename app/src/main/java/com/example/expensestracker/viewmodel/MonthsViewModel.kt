package com.example.expensestracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.models.Expense
import com.example.expensestracker.models.Month
import com.example.expensestracker.repository.MonthsRepository
import kotlinx.coroutines.launch

class MonthsViewModel(private val monthsRepository: MonthsRepository) : ViewModel() {
    val months: MutableLiveData<List<Month>> = MutableLiveData()
    val month: MutableLiveData<Month> = MutableLiveData()
    val expenses: MutableLiveData<List<Expense>> = MutableLiveData()

    fun getMonths() {
        viewModelScope.launch {
            val result = monthsRepository.getMonths()
            months.value = result.body()
        }
    }

    fun updateMonths(months: List<Month>) {
        viewModelScope.launch {
            monthsRepository.updateMonths(months)
        }
    }

    fun getMonth(id: Int) {
        viewModelScope.launch {
            val result = monthsRepository.getMonth(id)
            month.value = result.body()
        }
    }

    fun updateExpenses(id: Int, month: Month) {
        viewModelScope.launch {
            monthsRepository.updateExpenses(id, month)
        }
    }

    fun getExpenses(id: Int) {
        viewModelScope.launch {
            val result = monthsRepository.getExpenses(id)
            expenses.value = result.body()
        }
    }

    fun updateExpense(monthId: Int, expenseId: Int, expense: Expense) {
        viewModelScope.launch {
            monthsRepository.updateExpense(monthId, expenseId, expense)
        }
    }

    fun deleteExpense(monthId: Int, expenseId: Int) {
        viewModelScope.launch {
            monthsRepository.deleteExpense(monthId, expenseId)
        }
    }
}