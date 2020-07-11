package com.kvest.pamatky.storage.dto

import androidx.room.ColumnInfo

data class BasicSight (
    @ColumnInfo(name = "guid") val guid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lat") val lat: Float,
    @ColumnInfo(name = "lon") val lon: Float,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "site") val site: String?,
    @ColumnInfo(name = "instagram") val instagram: String?,
    @ColumnInfo(name = "facebook") val facebook: String?,
    @ColumnInfo(name = "youtube") val youtube: String?,
    @ColumnInfo(name = "profile_photo_small") val profilePhotoSmall: String
)