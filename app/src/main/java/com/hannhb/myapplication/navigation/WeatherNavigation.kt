package com.hannhb.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hannhb.myapplication.screen.about.AboutScreen
import com.hannhb.myapplication.screen.favourite.FavouriteScreen
import com.hannhb.myapplication.screen.main.MainScreen
import com.hannhb.myapplication.screen.main.MainViewModel
import com.hannhb.myapplication.screen.search.SearchScreen
import com.hannhb.myapplication.screen.setting.SettingScreen
import com.hannhb.myapplication.screen.setting.SettingViewModel
import com.hannhb.myapplication.screen.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreen.SPLASH_SCREEN.name) {
        composable(WeatherScreen.SPLASH_SCREEN.name) {
            SplashScreen(navController)
        }

        val routeMainScreen = WeatherScreen.MAIN_SCREEN.name
        composable("$routeMainScreen/{city}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )) {navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel: MainViewModel = hiltViewModel()
                val settingViewModel: SettingViewModel = hiltViewModel()
                MainScreen(navController, mainViewModel, settingViewModel, city)
            }

        }

        composable(WeatherScreen.SEARCH_SCREEN.name) {
            SearchScreen(navController)
        }

        composable(WeatherScreen.ABOUT_SCREEN.name) {
            AboutScreen(navController)
        }

        composable(WeatherScreen.FAVORITE_SCREEN.name) {
            FavouriteScreen(navController)
        }

        composable(WeatherScreen.SETTING_SCREEN.name) {
            val settingViewModel: SettingViewModel = hiltViewModel()
            SettingScreen(navController, settingViewModel)
        }
    }


}