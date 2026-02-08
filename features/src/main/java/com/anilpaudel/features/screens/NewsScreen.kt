package com.anilpaudel.features.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.domain.DataState
import com.anilpaudel.features.Routes
import com.anilpaudel.features.composables.ErrScreen
import com.anilpaudel.features.composables.NewsSingleItem
import com.anilpaudel.features.viewmodel.NewsViewModel
import kotlinx.serialization.json.Json
import logcat.logcat

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@Composable
fun NewsScreen(navController: NavController, vm: NewsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val newsFlow = vm.newsListData.collectAsStateWithLifecycle()

    val loading = remember {
        mutableStateOf(true)
    }
    val error = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(newsFlow.value) {
        when (newsFlow.value) {

            is DataState.Loading -> {
                loading.value = true
                error.value = false
            }

            is DataState.Success -> {
                loading.value = false
                error.value = false

            }

            is DataState.Error -> {
                loading.value = false
                error.value = true
            }

        }
    }

    BackHandler {
        (context as Activity).finish()
    }

    Scaffold { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            if (loading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else if (error.value) {

                logcat { "News list error" }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrScreen()
                }
            } else {
                val articleList = (newsFlow.value as DataState.Success<List<ArticleDto>>).result
                LazyColumn {
                    items(articleList.size) { index ->
                        NewsSingleItem(article = articleList[index]) { article ->
                            navController.navigate(
                                Routes.Webview(
                                    Json.encodeToString(article)
                                )
                            )
                        }
                    }
                }
            }
        }
    }


}

