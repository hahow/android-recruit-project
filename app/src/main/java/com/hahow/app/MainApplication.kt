package com.hahow.app

import android.app.Application
import com.hahow.di.useCaseModule
import com.hahow.di.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDateTime()
        initKoin()
    }

    /**
     * 初始化日期時間的判斷
     * */
    private fun initDateTime() {
        AndroidThreeTen.init(this)
    }

    /**
     * 初始化 Koin
     * */
    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(useCaseModule, viewModelModule))
        }
    }


}