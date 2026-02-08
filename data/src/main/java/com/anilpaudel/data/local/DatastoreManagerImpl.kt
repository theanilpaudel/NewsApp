package com.anilpaudel.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anilpaudel.data.Source
import com.anilpaudel.domain.DataStoreManager
import com.anilpaudel.domain.SourceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * Created by Anil Paudel on 26/08/2025.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sources")

class DatastoreManagerImpl(private val context: Context) : DataStoreManager {
    val SOURCE_KEY = stringPreferencesKey("source")

    override val sourceFlow: Flow<List<SourceDto>?> = context.dataStore.data.map { preferences ->
        preferences[SOURCE_KEY]?.let { sourceJson ->
            Json.decodeFromString<List<Source>>(sourceJson)
        }?.map {
            SourceDto(
                country = it.country,
                name = it.name,
                description = it.description,
                language = it.language,
                id = it.id,
                category = it.category,
                url = it.url
            )
        }
    }


    override suspend fun saveSources(sources: List<SourceDto>) {
        val sourceList = sources.map {
            Source(
                country = it.country,
                name = it.name,
                description = it.description,
                language = it.language,
                id = it.id,
                category = it.category,
                url = it.url

            )
        }
        val sourceJson = Json.encodeToString(sourceList)
        context.dataStore.edit { preferences ->
            preferences[SOURCE_KEY] = sourceJson
        }
    }
}