package com.example.expensestracker.data.network

import com.example.expensestracker.domain.models.Expense
import com.example.expensestracker.domain.models.Month
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface FirebaseService {
    @DELETE("data.json")
    suspend fun deleteAllData()

    @GET("months.json")
    suspend fun getYourMonths(): List<Month>

    @GET("expenses.json")
    suspend fun getYourExpenses(): List<Expense>

    @PUT("months.json")
    suspend fun backupMonths(@Body months: List<Month>): List<Month>

    @PUT("expenses.json")
    suspend fun backupExpenses(@Body expenses: List<Expense>): List<Expense>

    companion object {
        fun getInstance(): FirebaseService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://expensestracker-55519-default-rtdb.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create()
        }
    }
}