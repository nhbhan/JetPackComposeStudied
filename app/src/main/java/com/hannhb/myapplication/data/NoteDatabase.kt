package com.hannhb.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.hannhb.myapplication.model.Note
import com.hannhb.myapplication.util.DateConverter
import com.hannhb.myapplication.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDatabaseDao(): NoteDatabaseDao


}