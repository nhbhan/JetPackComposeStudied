package com.hannhb.movieapplication.navigation

import android.graphics.Movie
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hannhb.movieapplication.screen.detail.DetailScreen
import com.hannhb.movieapplication.screen.home.HomeScreen

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MovieScreen.HOME_SCREEN.name) {
        composable(MovieScreen.HOME_SCREEN.name) {
            HomeScreen(navController)
        }
        composable(MovieScreen.DETAIL_SCREEN.name + "/{movie}",
            arguments = listOf(navArgument(name = "movie"){ type = NavType.StringType})
        ) {backEntry ->
            DetailScreen(navController = navController,
                backEntry.arguments?.getString("movie"))
        }
    }
}