package com.kvest.pamatky.repository

import androidx.lifecycle.LiveData
import com.kvest.pamatky.storage.entity.SightEntity

interface SightsRepository {
    fun listenSights(): LiveData<List<SightEntity>>
    fun listenSight(guid: String): LiveData<SightEntity>
    suspend fun getSight(guid: String): SightEntity
    suspend fun updateSights(): Boolean
}