package com.anilpaudel.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.model.SourceResponse
import com.anilpaudel.newsapp.usecase.GetNewsListUC
import com.anilpaudel.newsapp.usecase.GetSavedSourceListUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsListUC: GetNewsListUC,
    private val savedSourceListUC: GetSavedSourceListUC
) : ViewModel() {

    init {
        getSavedSources()
    }

    private val _newsListData = MutableStateFlow<DataState<NewsResponse>>(DataState.Loading)
    val newsListData = _newsListData.asStateFlow()

    private val _sourceFlow = MutableStateFlow<DataState<SourceResponse>>(DataState.Loading)
    val sourceFlow = _sourceFlow.asStateFlow()

    fun getNews(sources: String?) {
        viewModelScope.launch {
            newsListUC(GetNewsListUC.Params(sources)).collectLatest {
                _newsListData.emit(it)
            }
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