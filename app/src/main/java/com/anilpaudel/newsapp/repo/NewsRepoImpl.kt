package com.anilpaudel.newsapp.repo

import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.model.SourceResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
interface NewsRepo {
    suspend fun getNews(sources: String?): Response<NewsResponse>
    suspend fun getSources(): Response<SourceResponse>
}

class NewsRepoImpl @Inject constructor(private val apiService: ApiService) : NewsRepo {

    override suspend fun getNews(sources: String?): Response<NewsResponse> {
        return apiService.getNews(sources = sources)
    }

    override suspend fun getSources(): Response<SourceResponse> {
        return apiService.getSource()
    }

}