package com.example.expensestracker.di

import com.example.expensestracker.data.database.MonthsDao
import com.example.expensestracker.data.repository.MonthsRepositoryImpl
import com.example.expensestracker.domain.repository.MonthsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMonthsRepository(monthsDao: MonthsDao): MonthsRepository {
        return MonthsRepositoryImpl(monthsDao)
    }
}