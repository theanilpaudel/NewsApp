package com.anilpaudel.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.domain.GetAllArticlesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getAllArticlesUC: GetAllArticlesUC,
) : ViewModel() {

    val allArticlesFlow: StateFlow<List<ArticleDto>?> = getAllArticlesUC().stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000)
    )

}