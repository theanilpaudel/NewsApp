package com.anilpaudel.features

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Anil Paudel on 25/08/2025.
 */
data class BottomNavItem(
    val route: Routes,
    val title: String,
    val icon: ImageVector
)