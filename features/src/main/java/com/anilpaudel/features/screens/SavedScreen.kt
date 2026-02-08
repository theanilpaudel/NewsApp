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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.anilpaudel.features.Routes
import com.anilpaudel.features.composables.ErrScreen
import com.anilpaudel.features.composables.NewsSingleItem
import com.anilpaudel.features.viewmodel.SavedViewModel
import kotlinx.serialization.json.Json
import logcat.logcat


@Composable
fun SavedScreen(navController: NavController, vm: SavedViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val newsFlow = vm.allArticlesFlow.collectAsStateWithLifecycle()

    BackHandler {
        (context as Activity).finish()
    }

    Scaffold { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            LazyColumn {
                newsFlow.value?.size?.let { size ->
                    items(size) { index ->
                        newsFlow.value?.let {
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

