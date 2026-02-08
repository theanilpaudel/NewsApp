package com.anilpaudel.features.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Anil Paudel on 26/08/2025.
 */
@Composable
fun ErrScreen(
    modifier: Modifier = Modifier,
    text: String = "Something went wrong" //todo

) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}