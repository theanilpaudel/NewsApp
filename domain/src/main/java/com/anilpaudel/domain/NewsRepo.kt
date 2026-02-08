package com.anilpaudel.domain

import kotlinx.coroutines.flow.Flow

/**
 * Created by Anil Paudel on 08/02/2026.
 */
interface NewsRepo {
    fun getAllArticles(): Flow<List<ArticleDto>?>
    suspend fun getNews(sources: String?): DataState<List<ArticleDto>?>
    suspend fun getSources(): DataState<List<SourceDto>?>
    suspend fun saveArticle(articleDto: ArticleDto): DataState<ArticleDto>
}