package com.hannhb.myapplication.network

import com.hannhb.myapplication.model.Weather
import com.hannhb.myapplication.model.WeatherX
import com.hannhb.myapplication.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather

}