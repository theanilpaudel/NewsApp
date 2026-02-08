package com.anilpaudel.domain

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
            emit(newsRepo.getSources())
        } catch (ex: Throwable) {
            ex.printStackTrace()
            emit(DataState.Error(Throwable(ex.message)))
        }
    }.flowOn(Dispatchers.IO)
}