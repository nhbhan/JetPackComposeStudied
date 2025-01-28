package com.hannhb.myapplication.screen.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hannhb.myapplication.data.DataOrException
import com.hannhb.myapplication.model.Weather
import com.hannhb.myapplication.model.WeatherX
import com.hannhb.myapplication.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel(){
    val data: MutableState<DataOrException<Weather, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception("")))

    suspend fun getWeather(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city)
    }

    init {
//        loadWeather()
    }

//    private fun loadWeather() {
//        getWeather("Seattle")
//    }
//
//    private fun getWeather(city: String) {
//        viewModelScope.launch {
//            if (city.isEmpty()) return@launch
//            data.value.isLoading = true
//            data.value = repository.getWeather(city)
//            if (data.value.toString().isNotEmpty()) data.value.isLoading = false
//        }
//        Log.d("GET", "getWeather: ${data.value.data.toString()}")
//    }
}