package com.kvest.pamatky.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kvest.pamatky.storage.entity.SightEntity

@Dao
interface SightDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSights(sights: List<SightEntity>)

    @Query("DELETE FROM sights")
    suspend fun deleteAll()

    @Query("SELECT * FROM sights")
    fun listenSights(): LiveData<List<SightEntity>>

    @Query("SELECT * FROM sights WHERE guid = :guid")
    fun listenSight(guid: String): LiveData<SightEntity>

    @Query("SELECT * FROM sights WHERE guid = :guid")
    suspend fun getSight(guid: String): SightEntity

    @Transaction
    suspend fun transaction(action: suspend SightDAO.() -> Unit) = action()
}