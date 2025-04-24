package com.hannhb.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.model.Unit

@Database(entities = [Favourite::class, Unit::class], version = 2)
abstract class WeatherDaoDatabase: RoomDatabase() {

    abstract fun WeatherDao(): WeatherDao
}