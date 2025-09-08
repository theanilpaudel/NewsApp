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
open class GetSourceListUC @Inject constructor(private val newsRepo: NewsRepo) {

    object Params

    operator fun invoke(params: Params) = flow {
        emit(DataState.Loading)
        try {
            val response = newsRepo.getSources()
            if (response.body() != null && response.isSuccessful) {
                emit(DataState.Success(response.body()!!))
            } else {
                DataState.Error(Throwable(response.message()))
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            emit(DataState.Error(Throwable(ex.message)))
        }
    }.flowOn(Dispatchers.IO)
}