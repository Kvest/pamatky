package com.kvest.pamatky.utils

import com.kvest.pamatky.api.model.Sight
import com.kvest.pamatky.di.POTO_URL
import com.kvest.pamatky.storage.entity.SightEntity

fun sightToEntity(sight: Sight): SightEntity {
    val photos = sight.photos.map {  String.format(POTO_URL, it.guid) }

    return SightEntity(
        guid = sight.guid,
        name = sight.name,
        slogan = sight.slogan,
        description = sight.description,
        lat = sight.lat,
        lon = sight.lon,
        profilePhoto = String.format(POTO_URL, sight.profilePhoto),
        profilePhotoSmall = String.format(POTO_URL, sight.profilePhotoSmall),
        photos = photos
    )
}
