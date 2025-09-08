package com.anilpaudel.newsapp.usecase

import com.anilpaudel.newsapp.data.local.DatastoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
open class GetSavedSourceListUC @Inject constructor(private val datastoreManager: DatastoreManager) {


    operator fun invoke() = datastoreManager.sourceFlow.flowOn(Dispatchers.IO)
}