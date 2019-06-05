package com.kvest.pamatky.repository

import android.util.Log
import com.kvest.pamatky.api.SightsApi

class SightsRepositoryImpl(
    private val sightsApi: SightsApi
) : SightsRepository {
    override suspend fun updateSights() {
        val sights = sightsApi.getSights()
        Log.d("KVEST_TAG", "downloaded ${sights.size}")
    }
}