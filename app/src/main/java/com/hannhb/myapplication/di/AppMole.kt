package com.hannhb.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hannhb.myapplication.data.WeatherDao
import com.hannhb.myapplication.data.WeatherDaoDatabase
import com.hannhb.myapplication.network.WeatherApi
import com.hannhb.myapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppMole {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDaoDatabase: WeatherDaoDatabase): WeatherDao
    = weatherDaoDatabase.WeatherDao()

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDaoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WeatherDaoDatabase::class.java,
            name = "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}