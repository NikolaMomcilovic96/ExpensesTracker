package com.example.expensestracker.viewmodel

import androidx.lifecycle.*
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.data.models.Month
import com.example.expensestracker.data.repository.MonthsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthsViewModel @Inject constructor(private val repository: MonthsRepositoryImpl) :
    ViewModel() {

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
            val result = repository.getMonths()
            _months.postValue(result)
        }
    }

    fun addMonth(month: Month) {
        viewModelScope.launch {
            repository.addMonth(month)
            getMonths()
        }
    }

    /*fun deleteMonth(monthId: Int) {
        viewModelScope.launch {
            repository.deleteMonth(monthId)
            getMonths()
        }
    }*/

    fun updateMonth(monthId: Int, name: String, total: Int) {
        viewModelScope.launch {
            repository.updateMonth(monthId, name, total)
        }
    }

    fun getExpenses(monthId: Int) {
        viewModelScope.launch {
            val result = repository.getExpenses(monthId)
            _expenses.postValue(result)
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.addExpense(expense)
            getExpenses(expense.monthId)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense.id)
            getExpenses(expense.monthId)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.updateExpense(expense.id, expense.title, expense.price)
            getExpenses(expense.monthId)
        }
    }
}