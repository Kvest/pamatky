package com.kvest.pamatky.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.storage.entity.SightEntity

@Dao
interface SightDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSights(sights: List<SightEntity>)

    @Query("DELETE FROM sights")
    suspend fun deleteAll()

    @Query("SELECT guid, name, lat, lon, profile_photo_small FROM sights")
    fun listenBasicSights(): LiveData<List<BasicSight>>

    @Query("SELECT * FROM sights WHERE guid = :guid")
    fun listenSight(guid: String): LiveData<SightEntity>

    @Transaction
    suspend fun transaction(action: suspend SightDAO.() -> Unit) = action()
}