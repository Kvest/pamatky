package com.kvest.pamatky.storage.dto

import androidx.room.ColumnInfo

data class BasicSight (
    @ColumnInfo(name = "guid") val guid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lat") val lat: Float,
    @ColumnInfo(name = "lon") val lon: Float,
    @ColumnInfo(name = "profile_photo_small") val profilePhotoSmall: String
)