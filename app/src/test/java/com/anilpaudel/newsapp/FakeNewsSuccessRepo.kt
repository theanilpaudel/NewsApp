package com.anilpaudel.newsapp

import com.anilpaudel.newsapp.model.Article
import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.model.Source
import com.anilpaudel.newsapp.model.SourceResponse
import com.anilpaudel.newsapp.repo.NewsRepo
import retrofit2.Response

/**
 * Created by Anil Paudel on 26/08/2025.
 */
class FakeNewsSuccessRepo : NewsRepo {

    private val fakeNewsResponse = NewsResponse(
        status = "ok",
        totalResults = 1,
        articles = listOf(
            Article(
                title = "Test Title",
                author = "Test Author",
                description = "Test Description",
                url = "https://example.com",
                urlToImage = null,
                content = "Test content",
                publishedAt = "2025-08-26T12:00:00Z"
            )
        )
    )

    private val fakeSourceResponse = SourceResponse(
        status = "ok",
        sources = listOf(
            Source(id = "1", name = "Test Source")
        )
    )

    override suspend fun getNews(sources: String?): Response<NewsResponse> {
        return Response.success(fakeNewsResponse)
    }

    override suspend fun getSources(): Response<SourceResponse> {
        return Response.success(fakeSourceResponse)
    }
}

