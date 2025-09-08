package com.anilpaudel.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.model.NewsResponse
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
class SavedViewModel @Inject constructor(
    private val newsDao: NewsDao,
) : ViewModel() {

    private val _savedArticleFlow = MutableStateFlow<DataState<NewsResponse>>(DataState.Loading)
    val savedArticleFlow = _savedArticleFlow.asStateFlow()

    init {
        getSavedArticle()
    }

    fun getSavedArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            _savedArticleFlow.emit(DataState.Success(NewsResponse(articles = newsDao.getAllArticles())))
        }

    }
}