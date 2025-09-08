package com.anilpaudel.newsapp

import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.model.SourceResponse
import com.anilpaudel.newsapp.repo.NewsRepo
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * Created by Anil Paudel on 26/08/2025.
 */
class FakeNewsErrorRepo : NewsRepo {

    override suspend fun getNews(sources: String?): Response<NewsResponse> {
        return Response.error(500, "".toResponseBody(null))
    }

    override suspend fun getSources(): Response<SourceResponse> {
        return Response.error(500, "".toResponseBody(null))
    }
}