package com.anilpaudel.newsapp.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.anilpaudel.newsapp.navigation.Routes

/**
 * Created by Anil Paudel on 25/08/2025.
 */
data class BottomNavItem(
    val route: Routes,
    val title: String,
    val icon: ImageVector
)
