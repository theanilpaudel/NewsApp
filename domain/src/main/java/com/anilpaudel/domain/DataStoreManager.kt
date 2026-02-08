package com.anilpaudel.domain

import kotlinx.coroutines.flow.Flow

/**
 * Created by Anil Paudel on 08/02/2026.
 */
interface DataStoreManager {
    val sourceFlow: Flow<List<SourceDto>?>
    suspend fun saveSources(sources: List<SourceDto>)
}