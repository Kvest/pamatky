package com.kvest.pamatky.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kvest.pamatky.storage.entity.SightEntity

@Dao
interface SightDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSights(sights: List<SightEntity>)

    @Query("DELETE FROM sights")
    suspend fun deleteAll()
}