package com.kvest.pamatky.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Photo(
    @Json(name = "Guid") val guid: String
)