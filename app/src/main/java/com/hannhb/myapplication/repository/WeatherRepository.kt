package com.hannhb.myapplication.repository

import android.util.Log
import com.hannhb.myapplication.data.DataOrException
import com.hannhb.myapplication.model.Weather
import com.hannhb.myapplication.model.WeatherX
import com.hannhb.myapplication.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            weatherApi.getWeather(query = cityQuery)
        } catch (e: Exception) {
            Log.e("Rex", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.e("inside", "getWeather: $response")
        return DataOrException(data = response)
    }
}