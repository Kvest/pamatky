package com.kvest.pamatky.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kvest.pamatky.storage.dao.SightDAO
import com.kvest.pamatky.storage.entity.SightEntity

const val DB_VERSION = 1
const val DB_NAME = "sights-db"

@Database(entities = [SightEntity::class], version = DB_VERSION)
abstract class SightDatabase : RoomDatabase() {
    abstract fun sightDao(): SightDAO
}