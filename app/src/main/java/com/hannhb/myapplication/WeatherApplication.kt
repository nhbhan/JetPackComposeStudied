package com.hannhb.myapplication

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }
}