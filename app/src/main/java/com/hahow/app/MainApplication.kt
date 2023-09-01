package com.hahow.app

import android.app.Application
import android.util.Log
import com.hahow.di.useCaseModule
import com.hahow.di.viewModelModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
        }
    }


}