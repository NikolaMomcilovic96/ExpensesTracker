package com.example.expensestracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.expensestracker.domain.models.Grocery

@Dao
interface GroceriesDao {

    @Query("SELECT * FROM grocery ORDER BY checked")
    suspend fun getAllGroceries(): List<Grocery>

    @Insert(onConflict = REPLACE)
    suspend fun addNewGrocery(grocery: Grocery)

    @Query("UPDATE grocery SET name = :name WHERE id = :groceryId")
    suspend fun updateGrocery(groceryId: Int, name: String)

    @Query("DELETE FROM grocery WHERE id = :groceryId")
    suspend fun deleteGrocery(groceryId: Int)

    @Query("UPDATE grocery SET checked = 1 WHERE id = :groceryId")
    suspend fun checkGrocery(groceryId: Int)

    @Query("UPDATE grocery SET checked = 0 WHERE id = :groceryId")
    suspend fun uncheckGrocery(groceryId: Int)
}