package com.example.task.utilities.di

import com.example.task.data.local.api.FakeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideFakeApi(): FakeApiService{
        return FakeApiService.getNetworkInstance()
    }
}