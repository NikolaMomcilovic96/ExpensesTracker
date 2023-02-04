package com.example.expensestracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.domain.models.Grocery
import com.example.expensestracker.domain.usecases.GroceriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceriesViewModel @Inject constructor(private val useCase: GroceriesUseCase) : ViewModel() {

    private val _groceries: MutableLiveData<List<Grocery>> = MutableLiveData()
    val groceries: LiveData<List<Grocery>>
        get() = _groceries

    fun getAllGroceries() {
        viewModelScope.launch {
            val result = useCase.getGroceries()
            _groceries.postValue(result)
        }
    }

    fun addNewGrocery(grocery: Grocery) {
        viewModelScope.launch {
            useCase.addGrocery(grocery)
            getAllGroceries()
        }
    }

    fun updateGrocery(groceryId: Int, name: String) {
        viewModelScope.launch {
            useCase.updateGrocery(groceryId, name)
            getAllGroceries()
        }
    }

    fun deleteGrocery(groceryId: Int) {
        viewModelScope.launch {
            useCase.deleteGrocery(groceryId)
            getAllGroceries()
        }
    }

    fun checkGrocery(groceryId: Int) {
        viewModelScope.launch {
            useCase.checkGrocery(groceryId)
            getAllGroceries()
        }
    }

    fun uncheckGrocery(groceryId: Int) {
        viewModelScope.launch {
            useCase.uncheckGrocery(groceryId)
            getAllGroceries()
        }
    }

    fun checkAll() {
        viewModelScope.launch {
            val allGroceries = useCase.getGroceries()
            for (g in allGroceries) {
                if (!g.checked) {
                    useCase.checkGrocery(g.id)
                }
            }
            getAllGroceries()
        }
    }
}