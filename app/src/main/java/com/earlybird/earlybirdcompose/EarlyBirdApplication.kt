package com.earlybird.earlybirdcompose

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EarlyBirdApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        try {
            // Firebase는 자동으로 초기화되므로 별도 초기화 코드 제거
            Log.d("EarlyBirdApp", "Application started successfully")
        } catch (e: Exception) {
            Log.e("EarlyBirdApp", "Error during application startup", e)
        }
    }
}