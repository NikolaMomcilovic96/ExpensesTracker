package com.example.expensestracker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.container).navigateUp()
    }

    override fun onStop() {
        super.onStop()

        sharedPreferences =
            getSharedPreferences("sharedPref", Context.MODE_PRIVATE)!!
        sharedPreferences.edit().putBoolean("isAuthorized", false).apply()
    }
}