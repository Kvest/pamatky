package com.kvest.pamatky.repository

import android.util.Log
import com.kvest.pamatky.api.SightsApi
import com.kvest.pamatky.storage.dao.SightDAO
import com.kvest.pamatky.utils.sightToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SightsRepositoryImpl(
    private val sightsApi: SightsApi,
    private val sightDAO: SightDAO
) : SightsRepository {
    override fun getBasicSightsList() = sightDAO.listenBasicSights()

    override suspend fun updateSights(): Boolean {
        try {
            //retrieve up-to-date sights
            val sights = sightsApi.getSights()

            //store sights to the local DB
            val entities = withContext(Dispatchers.Default) { sights.map(::sightToEntity) }

            sightDAO.transaction {
                deleteAll()
                insertSights(entities)
            }
        } catch (ex: Exception) {
            return false
        }

        return true
    }
}