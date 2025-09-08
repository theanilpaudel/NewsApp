package com.anilpaudel.newsapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.anilpaudel.newsapp.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * Created by Anil Paudel on 26/08/2025.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sources")

class DatastoreManager(private val context: Context) {
    val SOURCE_KEY = stringPreferencesKey("source")

    val sourceFlow: Flow<List<Source>?> = context.dataStore.data.map { preferences ->
        preferences[SOURCE_KEY]?.let { sourceJson ->
            Json.decodeFromString<List<Source>>(sourceJson)
        }
    }

    suspend fun saveSources(sources: List<Source>) {
        val sourceJson = Json.encodeToString(sources)
        context.dataStore.edit { preferences ->
            preferences[SOURCE_KEY] = sourceJson
        }
    }
}