package com.hannhb.myapplication.screen.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hannhb.myapplication.data.DataOrException
import com.hannhb.myapplication.model.Weather
import com.hannhb.myapplication.wigets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(isLoading = true)) {
        value = mainViewModel.getWeather("Seattle")
    }.value
    if (weatherData.isLoading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data, navController = navController)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city?.name.valueToDefault() + ", ${weather.city?.country.valueToDefault()}",
//            icon = Icons.Default.ArrowBack,
            navController = navController,
            elevation = 5.dp,
            onButtonClicked = {
                Log.d("TAG", "MainScaffold: Button clicked")
            })
    }) {
        MainContent(weather = weather)
    }
}

@Composable
fun MainContent(weather: Weather) {
    Text(text = weather.city.toString())
    
}

fun String?.valueToDefault(value: String = "") : String {
    return this ?: value
}