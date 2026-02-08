package com.anilpaudel.di

import android.content.Context
import com.anilpaudel.data.local.AppDatabase
import com.anilpaudel.data.local.DatastoreManagerImpl
import com.anilpaudel.data.local.NewsDao
import com.anilpaudel.data.remote.ApiService
import com.anilpaudel.data.remote.NewsRepoImpl
import com.anilpaudel.domain.DataStoreManager
import com.anilpaudel.domain.NewsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideNewsDao(@ApplicationContext context: Context): NewsDao {
        return AppDatabase.getInstance(context)?.newsDao()!!
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DatastoreManagerImpl(context)
    }

    @Provides
    fun provideNewsRepo(apiService: ApiService, newsDao: NewsDao): NewsRepo {
        return NewsRepoImpl(apiService, newsDao)
    }
}