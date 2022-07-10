package com.example.expensestracker.services

import com.example.expensestracker.models.Expense
import com.example.expensestracker.models.Month
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FirebaseService {
    @GET("months.json")
    suspend fun getMonths(): Response<List<Month>>

    @PUT("months.json")
    suspend fun updateMonths(
        @Body months: List<Month>
    ): Response<List<Month>>

    @GET("months/{id}.json")
    suspend fun getMonth(
        @Path("id") id: Int
    ): Response<Month>

    @PUT("months/{id}.json")
    suspend fun updateExpenses(
        @Path("id") id: Int,
        @Body month: Month
    ): Response<Month>

    @GET("months/{id}/expenses.json")
    suspend fun getExpenses(
        @Path("id") id: Int,
    ): Response<List<Expense>>

    @PUT("months/{monthId}/expenses/{expenseId}.json")
    suspend fun updateExpense(
        @Path("monthId") monthId: Int,
        @Path("expenseId") expenseId: Int,
        @Body expense: Expense
    ): Response<Expense>

    @DELETE("months/{monthId}/expenses/{expenseId}.json")
    suspend fun deleteExpense(
        @Path("monthId") monthId: Int,
        @Path("expenseId") expenseId: Int
    ): Response<Expense>

    companion object {
        val instance: FirebaseService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://expensestracker-55519-default-rtdb.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(FirebaseService::class.java)
        }
    }
}