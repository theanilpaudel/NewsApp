package com.anilpaudel.features

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.anilpaudel.domain.ArticleDto
import com.anilpaudel.features.screens.NewsScreen
import com.anilpaudel.features.screens.NewsWebViewScreen
import com.anilpaudel.features.screens.SavedScreen
import com.anilpaudel.features.screens.SourceScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

sealed interface Routes {
    @Serializable
    object Headlines : Routes

    @Serializable
    object Sources : Routes

    @Serializable
    object Saved : Routes

    @Serializable
    data class Webview(val article: String) : Routes
}


@Composable
fun MyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Routes.Headlines
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Routes.Headlines> {
            NewsScreen(navController)
        }
        composable<Routes.Sources> {
            SourceScreen()
        }
        composable<Routes.Saved> {
            SavedScreen(navController)
        }
        composable<Routes.Webview> { backStackEntry ->
            val value = backStackEntry.toRoute<Routes.Webview>()
            val article = Json.decodeFromString<ArticleDto>(value.article)
            NewsWebViewScreen(article)
        }
    }

}

val bottomNavItems = listOf(
    BottomNavItem(
        route = Routes.Headlines,
        title = "Headlines",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = Routes.Sources,
        title = "Sources",
        icon = Icons.Default.List
    ),
    BottomNavItem(
        route = Routes.Saved,
        title = "Saved",
        icon = Icons.Default.Favorite
    )
)

