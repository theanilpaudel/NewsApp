package com.anilpaudel.domain

import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class SaveSourceListUC @Inject constructor(private val dataStoreManager: DataStoreManager) {

    data class Params(val sources: List<SourceDto>)

    suspend operator fun invoke(params: Params) {
        dataStoreManager.saveSources(params.sources)
    }
}