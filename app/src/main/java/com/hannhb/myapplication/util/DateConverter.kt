package com.hannhb.myapplication.util

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun timeStampFromData(time: Date): Long {
        return time.time
    }

    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date {
        return Date(timestamp)
    }
}