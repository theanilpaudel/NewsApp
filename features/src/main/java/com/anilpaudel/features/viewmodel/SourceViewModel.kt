package com.anilpaudel.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.domain.DataState
import com.anilpaudel.domain.GetSavedSourceListUC
import com.anilpaudel.domain.GetSourceListUC
import com.anilpaudel.domain.SaveSourceListUC
import com.anilpaudel.domain.SourceDto
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

    private val _sourceFlow = MutableStateFlow<DataState<List<SourceDto>?>>(DataState.Loading)
    val sourceFlow = _sourceFlow.asStateFlow()

    init {
        getSources()
//        getSavedSources()
    }

    fun getSources() {
        viewModelScope.launch {
            sourceListUC(GetSourceListUC.Params).collect {
                _sourceFlow.emit(it)
            }
        }
    }

    fun saveSources(sources: List<SourceDto>) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSourcesUC(SaveSourceListUC.Params(sources))
        }
    }

    fun getSavedSources() {
        viewModelScope.launch(Dispatchers.IO) {
            savedSourceListUC().collect {
//                _sourceFlow.emit(it)
            }
        }
    }
}