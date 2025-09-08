package com.anilpaudel.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import logcat.AndroidLogcatLogger
import logcat.LogPriority

/**
 * Created by Anil Paudel on 25/08/2025.
 */
@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}

