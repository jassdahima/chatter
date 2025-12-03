package com.example.chatter

import android.app.Application
import com.example.chatter.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChatterApplication)
            modules(appModule)
        }
    }
}