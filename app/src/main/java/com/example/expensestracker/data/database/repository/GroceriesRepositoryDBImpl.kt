package com.example.expensestracker.data.database.repository

import com.example.expensestracker.domain.models.Grocery
import com.example.expensestracker.domain.repository.GroceriesRepository

class GroceriesRepositoryDBImpl : GroceriesRepository {
    override suspend fun addGrocery(grocery: Grocery) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGrocery(groceryId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getGroceries(): List<Grocery> {
        TODO("Not yet implemented")
    }

    override suspend fun checkGrocery(groceryId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun uncheckGrocery(groceryId: Int) {
        TODO("Not yet implemented")
    }
}