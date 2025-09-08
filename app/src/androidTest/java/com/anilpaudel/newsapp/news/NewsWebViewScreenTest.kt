package com.anilpaudel.newsapp.news

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.anilpaudel.newsapp.HiltTestActivity
import com.anilpaudel.newsapp.model.Article
import com.anilpaudel.newsapp.screens.NewsWebViewScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Anil Paudel on 26/08/2025.
 */
@HiltAndroidTest
class NewsWebViewScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup(){
        hiltRule.inject()
        composeRule.setContent {
            NewsWebViewScreen(article = Article(title = "Title 1"))
        }

    }

    @Test
    fun newsWebViewScreen_onSaveButton_saveArticleShowToast(){
        composeRule.onNodeWithText("Save").performClick()
        composeRule.onNodeWithText("Article saved").isDisplayed()

    }
}