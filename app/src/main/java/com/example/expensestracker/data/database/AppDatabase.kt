package com.example.expensestracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensestracker.data.database.models.Expense
import com.example.expensestracker.data.database.models.Month

@Database(
    version = 1,
    entities = [
        Month::class, Expense::class
    ]
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getMonthsDao(): MonthsDao
}