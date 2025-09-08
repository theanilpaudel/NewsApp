package com.anilpaudel.newsapp.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.anilpaudel.newsapp.composables.ErrScreen
import com.anilpaudel.newsapp.composables.NewsSingleItem
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.model.NewsResponse
import com.anilpaudel.newsapp.navigation.Routes
import com.anilpaudel.newsapp.viewmodel.SavedViewModel
import kotlinx.serialization.json.Json
import logcat.logcat


@Composable
fun SavedScreen(navController: NavController, vm: SavedViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val newsFlow = vm.savedArticleFlow.collectAsState()

    var newsResponse by remember {
        mutableStateOf(NewsResponse())
    }
    val loading = remember {
        mutableStateOf(true)
    }
    val error = remember {
        mutableStateOf(false)
    }

    BackHandler {
        (context as Activity).finish()
    }

    LaunchedEffect(newsFlow.value) {
        when (newsFlow.value) {

            is DataState.Loading -> {
                loading.value = true
                error.value = false
            }

            is DataState.Success -> {
                newsResponse = (newsFlow.value as DataState.Success<NewsResponse>).result
                loading.value = false
                error.value = false

            }

            is DataState.Error -> {
                loading.value = false
                error.value = true
            }

        }
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
                logcat {
                    "News list error"
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrScreen()
                }
            } else {
                LazyColumn {
                    newsResponse.articles?.size?.let { size ->
                        items(size) { index ->
                            newsResponse.articles?.let {
                                NewsSingleItem(article = it[index]) { article ->
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
    }


}

