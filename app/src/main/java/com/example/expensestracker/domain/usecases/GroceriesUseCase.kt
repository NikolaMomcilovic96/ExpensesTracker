package com.example.expensestracker.domain.usecases

import com.example.expensestracker.data.database.repository.GroceriesRepositoryDBImpl
import com.example.expensestracker.domain.models.Grocery
import javax.inject.Inject

class GroceriesUseCase @Inject constructor(private val repository: GroceriesRepositoryDBImpl) {
    suspend fun getGroceries(): List<Grocery> {
        return repository.getGroceries()
    }

    suspend fun addGrocery(grocery: Grocery) {
        repository.addGrocery(grocery)
    }

    suspend fun updateGrocery(groceryId: Int, name: String) {
        repository.updateGrocery(groceryId, name)
    }

    suspend fun deleteGrocery(groceryId: Int) {
        repository.deleteGrocery(groceryId)
    }

    suspend fun checkGrocery(groceryId: Int) {
        repository.checkGrocery(groceryId)
    }

    suspend fun uncheckGrocery(groceryId: Int) {
        repository.uncheckGrocery(groceryId)
    }
}