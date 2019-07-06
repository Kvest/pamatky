package com.kvest.pamatky.repository

import androidx.lifecycle.LiveData
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.storage.entity.SightEntity

interface SightsRepository {
    fun listenBasicSightsList(): LiveData<List<BasicSight>>
    suspend fun getSight(guid: String): SightEntity
    suspend fun updateSights(): Boolean
}