package com.kvest.pamatky

import android.app.Application
import com.kvest.pamatky.di.appModule
import com.kvest.pamatky.di.apiModule
import com.kvest.pamatky.di.dbModule
import com.kvest.pamatky.utils.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SightsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@SightsApplication)
            modules( listOf(appModule, apiModule, dbModule))
        }

        plantTimberTree()
    }

    private fun plantTimberTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}