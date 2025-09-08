package com.anilpaudel.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.model.Source
import com.anilpaudel.newsapp.model.SourceResponse
import com.anilpaudel.newsapp.usecase.GetSavedSourceListUC
import com.anilpaudel.newsapp.usecase.GetSourceListUC
import com.anilpaudel.newsapp.usecase.SaveSourceListUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@HiltViewModel
class SourceViewModel @Inject constructor(
    private val savedSourceListUC: GetSavedSourceListUC,
    private val sourceListUC: GetSourceListUC,
    private val saveSourcesUC: SaveSourceListUC,
) : ViewModel() {

    private val _sourceFlow = MutableStateFlow<DataState<SourceResponse>>(DataState.Loading)
    val sourceFlow = _sourceFlow.asStateFlow()

    init {
        getSources()
        getSavedSources()
    }

    fun getSources() {
        viewModelScope.launch {
            sourceListUC(GetSourceListUC.Params).collect {
                _sourceFlow.emit(it)
            }
        }
    }

    fun saveSources(sources: List<Source>) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSourcesUC(SaveSourceListUC.Params(sources))
        }
    }

    fun getSavedSources() {
        viewModelScope.launch(Dispatchers.IO) {
            savedSourceListUC().collect {
                _sourceFlow.emit(DataState.Success(SourceResponse(sources = it)))
            }
        }
    }
}