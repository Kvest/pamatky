package com.kvest.pamatky.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.storage.entity.SightEntity

@Dao
abstract class SightDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSights(sights: List<SightEntity>)

    @Query("DELETE FROM sights")
    abstract suspend fun deleteAll()

    @Query("SELECT guid, name, lat, lon, profile_photo_small FROM sights")
    abstract fun listenBasicSights(): LiveData<List<BasicSight>>

    @Query("SELECT * FROM sights WHERE guid = :guid")
    abstract fun listenSight(guid: String): LiveData<SightEntity>

    @Transaction
    open suspend fun transaction(action: suspend SightDAO.() -> Unit) = action()
}