package com.example.expensestracker.domain.repository

import com.example.expensestracker.domain.models.Grocery

interface GroceriesRepository {

    suspend fun addGrocery(grocery: Grocery)

    suspend fun deleteGrocery(groceryId: Int)

    suspend fun getGroceries(): List<Grocery>

    suspend fun checkGrocery(groceryId: Int)

    suspend fun uncheckGrocery(groceryId: Int)
}