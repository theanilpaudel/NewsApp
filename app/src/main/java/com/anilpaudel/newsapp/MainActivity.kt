package com.anilpaudel.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anilpaudel.features.MyNavHost
import com.anilpaudel.features.bottomNavItems
import com.anilpaudel.newsapp.ui.theme.NewsAppTheme
import com.anilpaudel.features.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.tertiary) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = "",
                                        tint = White
                                    )
                                },
                                label = {
                                    Text(item.title, color = White)
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == item.route::class.qualifiedName } == true,
                                onClick = {
                                    navController.navigate(item.route)
                                })
                        }

                    }

                }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MyNavHost(navController)
                    }
                }
            }
        }
    }
}
