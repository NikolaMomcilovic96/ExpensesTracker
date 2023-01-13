package com.example.expensestracker.data.network

import com.example.expensestracker.data.network.models.RestMonth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface FirebaseService {

    @PUT("data.json")
    suspend fun backupData(@Body data: List<RestMonth>): List<RestMonth>

    @DELETE("data.json")
    suspend fun deleteAllData(@Body data: List<RestMonth>)

    @GET("data.json")
    suspend fun getYourData(): List<RestMonth>

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