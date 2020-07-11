package com.kvest.pamatky.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Sight(
    @Json(name = "Guid") val guid: String,
    @Json(name = "Nazev") val name: String,
    @Json(name = "Slogan") val slogan: String,
    @Json(name = "Popis") val description: String,
    @Json(name = "ZemepisnaSirka") val lat: Float,
    @Json(name = "ZemepisnaDelka") val lon: Float,
    @Json(name = "Kontakt") val contacts: Contacts,
    @Json(name = "GuidFotoProfilova") val profilePhoto: String,
    @Json(name = "GuidFotoZmensena") val profilePhotoSmall: String,
    @Json(name = "Foto") val photos: List<Photo>
)

@JsonClass(generateAdapter = true)
class Contacts(
    @Json(name = "Telefon") val phone: String,
    @Json(name = "Www") val site: String,
    @Json(name = "Facebook") val facebook: String,
    @Json(name = "Youtube") val youtube: String,
    @Json(name = "Instagram") val instagram: String
)