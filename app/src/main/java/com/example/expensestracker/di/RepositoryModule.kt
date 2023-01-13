package com.example.expensestracker.di

import com.example.expensestracker.data.database.MonthsDao
import com.example.expensestracker.data.database.repository.MonthsRepositoryDBImpl
import com.example.expensestracker.data.network.FirebaseService
import com.example.expensestracker.data.network.repository.MonthsRepositoryNetworkImpl
import com.example.expensestracker.di.qualifier.DB
import com.example.expensestracker.di.qualifier.Rest
import com.example.expensestracker.domain.repository.MonthsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @DB
    @Provides
    @Singleton
    fun provideMonthsRepository(monthsDao: MonthsDao): MonthsRepository {
        return MonthsRepositoryDBImpl(monthsDao)
    }

    @Rest
    @Provides
    @Singleton
    fun provideMonthsNetworkRepository(firebaseService: FirebaseService): MonthsRepository {
        return MonthsRepositoryNetworkImpl(firebaseService)
    }
}