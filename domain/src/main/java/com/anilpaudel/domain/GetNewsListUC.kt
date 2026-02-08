package com.anilpaudel.domain

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
        emit(newsRepo.getNews(params.sources))

    }.flowOn(Dispatchers.IO)
}