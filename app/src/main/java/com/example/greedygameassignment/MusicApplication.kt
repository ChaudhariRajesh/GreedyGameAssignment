package com.example.greedygameassignment

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

//Application class of the project

@HiltAndroidApp
class MusicApplication : Application()
{
    override fun onCreate() {
        super.onCreate()

        //Set the dark theme for the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}