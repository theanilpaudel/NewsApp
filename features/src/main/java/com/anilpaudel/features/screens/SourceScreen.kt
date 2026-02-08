package com.anilpaudel.features.screens

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anilpaudel.domain.DataState
import com.anilpaudel.domain.SourceDto
import com.anilpaudel.features.composables.ErrScreen
import com.anilpaudel.features.composables.SourceSingleItem
import com.anilpaudel.features.viewmodel.SourceViewModel
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
    val sourceFlow = vm.sourceFlow.collectAsStateWithLifecycle()

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
                    val sourceDtos = (sourceFlow.value as DataState.Success<List<SourceDto>>).result
                    LazyColumn {
                        items(sourceDtos.size) { index ->
                            sourceDtos.let { sources ->
                                SourceSingleItem(source = sources[index]) { clickedSource, isChecked ->
                                    val updatedSources = sources.toMutableList()
                                    updatedSources[index] =
                                        clickedSource.copy(isSaved = isChecked)
                                }
                            }
                        }
                    }
                }
            }
            Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
                val toSaveSources =
                    (sourceFlow.value as DataState.Success<List<SourceDto>>).result?.filter { it.isSaved }
                toSaveSources?.let {
                    vm.saveSources(it)
                }
            }) {
                Text("Save")
            }
        }
    }


}

