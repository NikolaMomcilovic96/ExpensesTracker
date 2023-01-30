package com.example.expensestracker.di

import android.content.Context
import androidx.room.Room
import com.example.expensestracker.data.database.AppDatabase
import com.example.expensestracker.data.database.GroceriesDao
import com.example.expensestracker.data.database.MonthsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonthsDao(appDatabase: AppDatabase): MonthsDao {
        return appDatabase.getMonthsDao()
    }

    @Provides
    @Singleton
    fun provideGroceryDao(appDatabase: AppDatabase): GroceriesDao {
        return appDatabase.getGroceriesDao()
    }

    companion object {
        private const val DB_NAME = "expenses_tracker_app_database.db"
    }
}