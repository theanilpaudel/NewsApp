package com.anilpaudel.domain

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val title: String,
    val url: String? = null,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val country: String? = null,
    val name: String? = null,
    val description: String? = null,
    val language: String? = null,
    val id: String? = null,
    val category: String? = null,
    val url: String? = null,
    val isSaved: Boolean = false
)
