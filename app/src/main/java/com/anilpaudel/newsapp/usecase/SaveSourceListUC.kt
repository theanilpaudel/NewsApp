package com.anilpaudel.newsapp.usecase

import com.anilpaudel.newsapp.data.local.DatastoreManager
import com.anilpaudel.newsapp.model.Source
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class SaveSourceListUC @Inject constructor(private val datastoreManager: DatastoreManager) {

    data class Params(val sources: List<Source>)

    suspend operator fun invoke(params: Params) {
        datastoreManager.saveSources(params.sources)
    }
}