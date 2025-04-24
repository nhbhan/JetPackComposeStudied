package com.hannhb.myapplication.repository

import com.hannhb.myapplication.data.WeatherDao
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()
    suspend fun getFavById(city: String) = weatherDao.getFavById(city)
    suspend fun updateFav(favourite: Favourite) = weatherDao.updateFav(favourite)
    suspend fun insertFav(favourite: Favourite) = weatherDao.insertFav(favourite)
    suspend fun deleteFav(favourite: Favourite)= weatherDao.deleteFav(favourite)
    suspend fun deleteAllFav() = weatherDao.deleteAllFav()
    fun getSettings(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

}