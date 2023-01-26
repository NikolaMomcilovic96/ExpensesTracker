package com.example.expensestracker.data.database.repository

import com.example.expensestracker.data.database.GroceriesDao
import com.example.expensestracker.domain.models.Grocery
import com.example.expensestracker.domain.repository.GroceriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroceriesRepositoryDBImpl @Inject constructor(private val groceriesDao: GroceriesDao) :
    GroceriesRepository {
    override suspend fun addGrocery(grocery: Grocery) {
        groceriesDao.addNewGrocery(grocery)
    }

    override suspend fun updateGrocery(groceryId: Int, name: String) {
        groceriesDao.updateGrocery(groceryId, name)
    }

    override suspend fun deleteGrocery(groceryId: Int) {
        groceriesDao.deleteGrocery(groceryId)
    }

    override suspend fun getGroceries(): List<Grocery> = withContext(Dispatchers.IO) {
        return@withContext groceriesDao.getAllGroceries()
    }

    override suspend fun checkGrocery(groceryId: Int) {
        groceriesDao.checkGrocery(groceryId)
    }

    override suspend fun uncheckGrocery(groceryId: Int) {
        groceriesDao.uncheckGrocery(groceryId)
    }
}