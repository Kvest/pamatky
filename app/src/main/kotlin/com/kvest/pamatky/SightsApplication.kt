package com.kvest.pamatky

import android.app.Application
import com.kvest.pamatky.di.appModule
import com.kvest.pamatky.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SightsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@SightsApplication)
            modules( listOf(appModule, apiModule))
        }
    }
}