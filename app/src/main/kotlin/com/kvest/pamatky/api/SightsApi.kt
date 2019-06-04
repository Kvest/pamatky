package com.kvest.pamatky.api

import com.kvest.pamatky.api.model.Sight
import retrofit2.http.POST

interface SightsApi {
    @POST("Api/v1/Objekt/Detailne")
    suspend fun getSights(): List<Sight>
}