package com.kvest.pamatky.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kvest.pamatky.storage.converter.ListOfStringConverter

@Entity(tableName = "sights")
@TypeConverters(ListOfStringConverter::class)
data class SightEntity(
    @PrimaryKey @ColumnInfo(name = "guid") val guid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "slogan") val slogan: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "lat") val lat: Float,
    @ColumnInfo(name = "lon") val lon: Float,
    @ColumnInfo(name = "profile_photo") val profilePhoto: String,
    @ColumnInfo(name = "profile_photo_small") val profilePhotoSmall: String,
    @ColumnInfo(name = "photos") val photos: List<String>
) {
    val allPhotos: List<String>
        get() = listOf(profilePhoto) + photos
}

