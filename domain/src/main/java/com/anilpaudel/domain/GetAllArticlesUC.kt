package com.anilpaudel.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Anil Paudel on 08/02/2026.
 */
class GetAllArticlesUC @Inject constructor(private val newsRepo: NewsRepo) {

     operator fun invoke(): Flow<List<ArticleDto>?> {
        return newsRepo.getAllArticles()

    }
}