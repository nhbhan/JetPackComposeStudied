package com.hannhb.myapplication.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timeToDate(time: Long): String {
    val date = Date(time)
    val formatter = SimpleDateFormat("EEE, dd MM YYYY", Locale.getDefault())
    return formatter.format(date)
}