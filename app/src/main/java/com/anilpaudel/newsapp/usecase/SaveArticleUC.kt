package com.anilpaudel.newsapp.usecase

import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.model.Article
import com.anilpaudel.newsapp.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class SaveArticleUC @Inject constructor(private val newsDao: NewsDao) {

    data class Params(val article: Article)

    operator fun invoke(params: Params) = flow {
        emit(DataState.Loading)
        try {
            val response = newsDao.insertArticle(params.article)
            if (response == 1L) {
                emit(DataState.Success(params.article))
            } else {
                DataState.Error(Throwable("Something went wrong")) //todo use some utils
            }
        } catch (ex: Throwable) {
            emit(DataState.Error(Throwable(ex.message)))
        }
    }.flowOn(Dispatchers.IO)

}