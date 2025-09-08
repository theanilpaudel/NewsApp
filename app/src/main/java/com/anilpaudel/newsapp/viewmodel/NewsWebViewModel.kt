package com.anilpaudel.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.model.Article
import com.anilpaudel.newsapp.model.DataState
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
class NewsWebViewModel @Inject constructor(
    private val newsDao: NewsDao,
) : ViewModel() {

    private val _saveArticleFlow = MutableStateFlow<DataState<Article>>(DataState.Loading)
    val saveArticleFlow = _saveArticleFlow.asStateFlow()

    private val _getArticleFlow = MutableStateFlow<DataState<Article>>(DataState.Loading)
    val getArticleFlow = _getArticleFlow.asStateFlow()

    private val _deleteArticleFlow = MutableStateFlow<DataState<String>>(DataState.Loading)
    val deleteArticleFlow = _deleteArticleFlow.asStateFlow()


    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsDao.insertArticle(article)
            _saveArticleFlow.emit(DataState.Success(article))
        }
    }

    fun getArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedArticle = newsDao.getArticle(article.title)
            _getArticleFlow.emit(DataState.Success(savedArticle))
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsDao.deleteArticle(article.title)
            _deleteArticleFlow.emit(DataState.Success("Success"))
        }
    }
}