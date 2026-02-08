package com.anilpaudel.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Anil Paudel on 08/02/2026.
 */

@Serializable
data class NewsResponse(
    val totalResults: Int? = null,
    val articles: List<Article>? = null,
    val status: String? = null
)

@Entity
@Serializable
data class Article(
    val publishedAt: String? = null,
    val author: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
    val description: String? = null,
    @PrimaryKey
    val title: String,
    val url: String? = null,
    val content: String? = null
)


@Serializable
data class Source(
    val country: String? = null,
    val name: String? = null,
    val description: String? = null,
    val language: String? = null,
    val id: String? = null,
    val category: String? = null,
    val url: String? = null,
    val isSaved: Boolean = false //would be better to separate to a dto
)

@Serializable
data class SourceResponse(
    val sources: List<Source>? = null,
    val status: String? = null
)
