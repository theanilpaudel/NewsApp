package com.anilpaudel.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anilpaudel.newsapp.model.Article

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@Dao
abstract class NewsDao {

    @Query("SELECT * from Article")
    abstract fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertArticle(article: Article): Long

    @Query("SELECT * from Article where :title=title")
    abstract fun getArticle(title: String): Article

    @Query("DELETE from Article where :title=title")
    abstract fun deleteArticle(title: String)

}