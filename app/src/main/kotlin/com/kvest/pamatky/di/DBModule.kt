package com.kvest.pamatky.di

import androidx.room.Room
import com.kvest.pamatky.storage.DB_NAME
import com.kvest.pamatky.storage.SightDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SightDatabase::class.java, DB_NAME
        ).build()
    }

    single {
        val db: SightDatabase = get()
        db.sightDao()
    }
}