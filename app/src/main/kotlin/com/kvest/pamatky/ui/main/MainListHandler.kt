package com.kvest.pamatky.ui.main

import com.kvest.pamatky.storage.entity.SightEntity

interface MainListHandler {
    fun onSightSelected(sight: SightEntity)
    fun onShowOnMap(sight: SightEntity)
    fun onShowInWaze(sight: SightEntity)
    fun onCall(sight: SightEntity)
    fun onShowSite(sight: SightEntity)
    fun onShowInstargam(sight: SightEntity)
    fun onShowFacebook(sight: SightEntity)
}