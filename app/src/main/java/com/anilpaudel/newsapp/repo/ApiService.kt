package com.anilpaudel.newsapp.repo

import com.anilpaudel.newsapp.BuildConfig
import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.model.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Anil Paudel on 25/08/2025.
 * would be better to add an okhttp interceptor to add the api key to every request
 */
interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("language") country: String = "en",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
        @Query("sources",encoded = true) sources: String? = null
    ): Response<NewsResponse>

    @GET("v2/top-headlines/sources")
    suspend fun getSource(
        @Query("language") country: String = "en",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): Response<SourceResponse>
}