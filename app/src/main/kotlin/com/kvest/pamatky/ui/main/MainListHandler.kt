package com.kvest.pamatky.ui.main

import com.kvest.pamatky.storage.dto.BasicSight

interface MainListHandler {
    fun onSightSelected(sight: BasicSight)
    fun onShowOnMap(sight: BasicSight)
    fun onShowInWaze(sight: BasicSight)
    fun onCall(sight: BasicSight)
    fun onShowSite(sight: BasicSight)
    fun onShowInstargam(sight: BasicSight)
    fun onShowFacebook(sight: BasicSight)
}