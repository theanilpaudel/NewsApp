package com.anilpaudel.newsapp.screens

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.anilpaudel.newsapp.model.Article
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.viewmodel.NewsWebViewModel
import kotlinx.coroutines.launch
import logcat.logcat

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@Composable
fun NewsWebViewScreen(article: Article, vm: NewsWebViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val articleFlow = vm.getArticleFlow.collectAsState()
    val deleteArticleFlow = vm.deleteArticleFlow.collectAsState()

    val articleAlreadySaved = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        vm.getArticle(article)
    }

    LaunchedEffect(Unit) {
        vm.saveArticleFlow.collect {
            when (it) {
                is DataState.Error -> {
                    logcat { "Error" }
                }

                DataState.Loading -> {
                    logcat { "loading" }
                }

                is DataState.Success -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Article saved")
                    }
                }
            }
        }

    }

    LaunchedEffect(articleFlow.value) {
        when (articleFlow.value) {
            is DataState.Success -> {
                val savedArticle = (articleFlow.value as DataState.Success<Article>).result
                if (savedArticle != null && savedArticle.title == article.title) {
                    articleAlreadySaved.value = true
                }
            }

            else -> {}
        }

    }

    LaunchedEffect(deleteArticleFlow.value) {
        when (deleteArticleFlow.value) {
            is DataState.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Article deleted")
                }
            }

            else -> {}
        }
    }



    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(5.dp)) {

                AndroidView(
                    modifier = Modifier
                        .fillMaxSize(),
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true

                        }
                    },
                    update = { webView ->
                        article.url?.let { articleUrl ->
                            webView.loadUrl(articleUrl)
                        }
                    }
                )
            }
            Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
                //can be moved to vm
                if (articleAlreadySaved.value) {
                    vm.deleteArticle(article)
                } else {
                    vm.saveArticle(article)
                }
            }) {
                val txt = if (articleAlreadySaved.value) "Delete" else "Save"
                Text(txt)
            }
        }
    }

}