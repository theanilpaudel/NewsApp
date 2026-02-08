package com.anilpaudel.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class GetSavedSourceListUC @Inject constructor(private val datastoreManager: DataStoreManager) {


    operator fun invoke(): Flow<List<SourceDto>?> {
        val flow = datastoreManager.sourceFlow.map { list ->
            val dtos = list?.map { source ->
                SourceDto(
                    country = source.country,
                    name = source.name,
                    description = source.description,
                    language = source.language,
                    id = source.id,
                    category = source.category,
                    url = source.url
                )
            }
            dtos
        }
        return flow

    }
}