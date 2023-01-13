package com.example.expensestracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month

@Dao
interface MonthsDao {

    @Query("SELECT * FROM month")
    suspend fun getMonths(): List<Month>

    @Insert(onConflict = REPLACE)
    suspend fun addMonth(month: Month)

    @Query("DELETE FROM month WHERE id=:monthId")
    suspend fun deleteMonth(monthId: Int)

    @Query("UPDATE month SET name = :name, total = :total WHERE id = :monthId")
    suspend fun updateMonth(monthId: Int, name: String, total: Int)

    @Query("SELECT * FROM expense WHERE monthId = :monthId")
    suspend fun getExpenses(monthId: Int): List<Expense>

    @Insert(onConflict = REPLACE)
    suspend fun addExpense(expense: Expense)

    @Query("DELETE FROM expense WHERE id=:expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Query("UPDATE expense SET title = :title, price = :price WHERE id = :expenseId")
    suspend fun updateExpense(expenseId: Int, title: String, price: Int)

    @Query("DELETE FROM Expense")
    suspend fun deleteAllExpenses()

    @Query("DELETE FROM Month")
    suspend fun deleteAllMonths()
}