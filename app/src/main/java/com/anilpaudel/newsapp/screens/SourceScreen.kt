package com.anilpaudel.newsapp.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.anilpaudel.newsapp.composables.ErrScreen
import com.anilpaudel.newsapp.composables.SourceSingleItem
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.model.SourceResponse
import com.anilpaudel.newsapp.viewmodel.SourceViewModel
import logcat.logcat

/**
 * Created by Anil Paudel on 25/08/2025.
 * there are few UI issues in this screen
 * 1. selected sources appear as soon as selected
 * 2. after going back to this screen they disappear
 * however they are already saved in datastore, just an UI issue
 * the news in NewsScreen can still be filtered by the sources in datastore
 */
@Composable
fun SourceScreen(vm: SourceViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val sourceFlow = vm.sourceFlow.collectAsState()

    var sourceResponse by remember {
        mutableStateOf(SourceResponse())
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

    LaunchedEffect(sourceFlow.value) {
        when (sourceFlow.value) {

            is DataState.Loading -> {
                loading.value = true
                error.value = false
            }

            is DataState.Success -> {
                sourceResponse = (sourceFlow.value as DataState.Success<SourceResponse>).result
                logcat {
                    "Saved ${sourceResponse.sources?.filter { it.isSaved }?.size} sources"
                }
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

        Box(modifier = Modifier.padding(innerPadding)) {
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
                        sourceResponse.sources?.size?.let { size ->
                            items(size) { index ->
                                sourceResponse.sources?.let { sources ->
                                    SourceSingleItem(source = sources[index]) { clickedSource, isChecked ->
                                        val updatedSources = sources.toMutableList()
                                        updatedSources[index] =
                                            clickedSource.copy(isSaved = isChecked)
                                        sourceResponse =
                                            sourceResponse.copy(sources = updatedSources)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
                val toSaveSources = sourceResponse.sources?.filter { it.isSaved }
                toSaveSources?.let {
                    vm.saveSources(it)
                }
            }) {
                Text("Save")
            }
        }
    }


}

