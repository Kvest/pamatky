package com.kvest.pamatky.ui.main

import com.kvest.pamatky.storage.dto.BasicSight

interface MainListHandler {
    fun onSightSelected(sight: BasicSight)
    fun onShowOnMap(sight: BasicSight)
}