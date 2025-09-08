package com.anilpaudel.newsapp.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anilpaudel.newsapp.data.AppDatabase
import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.model.Article
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anil Paudel on 28/08/2025.
 */
@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        newsDao = database.newsDao()

    }

    @Test
    fun insertArticle_shouldReturnLong(){
        val article = Article(
            title = "Test Title",
            author = "Test Author",
            description = "Test Description",
            url = "https://example.com",
            urlToImage = null,
            content = "Test content",
            publishedAt = "2025-08-26T12:00:00Z"
        )
        val rowId = newsDao.insertArticle(article)
        assertThat(rowId).isGreaterThan(0)

        val savedArticle = newsDao.getAllArticles().first()
        assertThat(savedArticle.title).isEqualTo(article.title)
    }


    @After
    fun cleanup(){
        database.close()
    }
}