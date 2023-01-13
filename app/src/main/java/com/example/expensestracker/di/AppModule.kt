package com.example.expensestracker.di

import android.content.Context
import com.example.expensestracker.data.network.FirebaseService
import com.example.expensestracker.domain.services.NetworkConnectionService
import com.example.expensestracker.domain.services.NetworkConnectionServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkConnectionService(@ApplicationContext context: Context): NetworkConnectionService {
        return NetworkConnectionServiceImpl(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseService(): FirebaseService = FirebaseService.getInstance()
}