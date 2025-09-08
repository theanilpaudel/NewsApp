package com.anilpaudel.newsapp.di

import android.content.Context
import com.anilpaudel.newsapp.data.AppDatabase
import com.anilpaudel.newsapp.data.local.DatastoreManager
import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.repo.ApiService
import com.anilpaudel.newsapp.repo.NewsRepo
import com.anilpaudel.newsapp.repo.NewsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Anil Paudel on 25/08/2025.
 */
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
    fun provideDataStoreManager(@ApplicationContext context: Context): DatastoreManager {
        return DatastoreManager(context)
    }

    @Provides
    fun provideNewsRepo(apiService: ApiService): NewsRepo{
        return NewsRepoImpl(apiService)
    }
}