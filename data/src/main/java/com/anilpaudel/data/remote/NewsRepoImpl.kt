package com.anilpaudel.data.remote

import com.anilpaudel.data.Article
import com.anilpaudel.data.local.NewsDao
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.domain.DataState
import com.anilpaudel.domain.NewsRepo
import com.anilpaudel.domain.SourceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */


class NewsRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) : NewsRepo {

    override fun getAllArticles(): Flow<List<ArticleDto>?> {
        val articleDtoFlow = newsDao.getAllArticlesAsFlow().map {
            val articleDtos = it.map {
                ArticleDto(
                    publishedAt = it.publishedAt,
                    author = it.author,
                    urlToImage = it.urlToImage,
                    description = it.description,
                    title = it.title,
                    url = it.url
                )
            }
            articleDtos

        }
        return articleDtoFlow
    }

    override suspend fun getNews(sources: String?): DataState<List<ArticleDto>?> {
        try {
            val response = apiService.getNews(sources = sources)
            return if (response.isSuccessful) {
                val dtos = response.body()?.articles?.map {
                    ArticleDto(
                        publishedAt = it.publishedAt,
                        author = it.author,
                        urlToImage = it.urlToImage,
                        description = it.description,
                        title = it.title,
                        url = it.url,
                        content = it.content
                    )
                }
                DataState.Success(dtos)
            } else {
                DataState.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            return DataState.Error(Throwable(e.message))
        }


    }

    override suspend fun getSources(): DataState<List<SourceDto>?> {
        try {
            val response = apiService.getSource()
            return if (response.isSuccessful) {
                val dtos = response.body()?.sources?.map {
                    SourceDto(
                        country = it.country,
                        name = it.name,
                        description = it.description,
                        language = it.language,
                        id = it.id,
                        category = it.category,
                        url = it.url
                    )
                }
                DataState.Success(dtos)
            } else {
                DataState.Error(Throwable(response.message()))

            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataState.Error(Throwable(e.message))
        }

    }

    override suspend fun saveArticle(articleDto: ArticleDto): DataState<ArticleDto> {
        try {
            val article = Article(
                publishedAt = articleDto.publishedAt,
                author = articleDto.author,
                urlToImage = articleDto.urlToImage,
                description = articleDto.description,
                title = articleDto.title,
                url = articleDto.url
            )
            newsDao.insertArticle(article)
            return DataState.Success(articleDto)
        } catch (e: Exception) {
            return DataState.Error(Throwable(e.message))

        }
    }

}