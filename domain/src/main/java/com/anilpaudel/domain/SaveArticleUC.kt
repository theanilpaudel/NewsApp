package com.anilpaudel.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class SaveArticleUC @Inject constructor(private val newsRepo: NewsRepo) {

    data class Params(val articleDto: ArticleDto)

    operator fun invoke(params: Params) = flow {
        emit(DataState.Loading)
        try {
            val response = newsRepo.saveArticle(params.articleDto)
            emit(response)
        } catch (ex: Throwable) {
            emit(DataState.Error(Throwable(ex.message)))
        }
    }.flowOn(Dispatchers.IO)

}