package com.hannhb.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl ")
    fun getFavourites(): Flow<List<Favourite>>

    @Query("SELECT * from fav_tbl where city=:city")
    suspend fun getFavById(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFav(favourite: Favourite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFav()

    @Delete
    suspend fun deleteFav(favourite: Favourite)

    @Query("SELECT * from setting_tbl ")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from setting_tbl")
    suspend fun deleteAllUnits()
}
