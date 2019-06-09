package com.kvest.pamatky.repository

import androidx.lifecycle.LiveData
import com.kvest.pamatky.storage.dto.BasicSight

interface SightsRepository {
    fun getBasicSightsList(): LiveData<List<BasicSight>>
    suspend fun updateSights(): Boolean
}