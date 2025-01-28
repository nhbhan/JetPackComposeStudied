package com.hannhb.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hannhb.myapplication.screen.main.MainScreen
import com.hannhb.myapplication.screen.main.MainViewModel
import com.hannhb.myapplication.screen.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreen.SPLASH_SCREEN.name) {
        composable(WeatherScreen.SPLASH_SCREEN.name) {
            SplashScreen(navController)
        }

        composable(WeatherScreen.MAIN_SCREEN.name) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(navController, mainViewModel)
        }
    }


}