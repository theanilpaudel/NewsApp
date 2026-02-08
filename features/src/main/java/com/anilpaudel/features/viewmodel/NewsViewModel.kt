package com.anilpaudel.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.domain.DataState
import com.anilpaudel.domain.GetNewsListUC
import com.anilpaudel.domain.GetSavedSourceListUC
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

    private val _newsListData = MutableStateFlow<DataState<List<ArticleDto>?>>(DataState.Loading)
    val newsListData = _newsListData.asStateFlow()

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
                val sources = it?.mapNotNull {
                    it.id
                }?.joinToString(",")
                getNews(sources)
            }
        }
    }
}