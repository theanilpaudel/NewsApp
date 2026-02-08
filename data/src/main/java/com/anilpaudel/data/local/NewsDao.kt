package com.anilpaudel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anilpaudel.data.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@Dao
interface NewsDao {

    @Query("SELECT * from Article")
    fun getAllArticlesAsFlow(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article): Long

    @Query("SELECT * from Article where :title=title")
    fun getArticle(title: String): Article

    @Query("DELETE from Article where :title=title")
    fun deleteArticle(title: String)

}