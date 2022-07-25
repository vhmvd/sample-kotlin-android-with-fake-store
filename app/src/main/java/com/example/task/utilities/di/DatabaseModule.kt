package com.example.task.utilities.di

import android.content.Context
import com.example.task.data.local.AppDatabase
import com.example.task.data.models.dao.CartDao
import com.example.task.data.models.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDB(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDB(context)
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.CartDao()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.ProductDao()
    }
}