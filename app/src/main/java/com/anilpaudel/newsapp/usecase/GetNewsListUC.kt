package com.anilpaudel.newsapp.usecase

import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
class GetNewsListUC @Inject constructor(private val newsRepo: NewsRepo) {

    data class Params(val sources: String? = null)

    operator fun invoke(params: Params) = flow {
        emit(DataState.Loading)
        try {
            val response = newsRepo.getNews(params.sources)
            if (response.body() != null && response.isSuccessful) {
                emit(DataState.Success(response.body()!!))
            } else {
                emit(DataState.Error(Throwable(response.message())))
            }
        } catch (ex: Throwable) {
            emit(DataState.Error(Throwable(ex.message)))
        }
    }.flowOn(Dispatchers.IO)
}