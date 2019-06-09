package com.kvest.pamatky.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val sightsRepository: SightsRepository
) : ViewModel(), MainListHandler {
    val sights by lazy(LazyThreadSafetyMode.NONE) { sightsRepository.getBasicSightsList() }
    val events by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<Event>() }

    init {
        viewModelScope.launch {
            val result = sightsRepository.updateSights()

            if (!result) {
                events.value = Event.RefreshFailed
            }
        }
    }

    override fun onSightSelected(sight: BasicSight) {
        events.value = Event.ShowSightDetails(sight.guid)
    }

    override fun onShowOnMap(sight: BasicSight) {
        events.value = Event.ShowSightOnMap(sight.lat, sight.lon)
    }

    sealed class Event {
        object RefreshFailed : Event()
        class ShowSightOnMap(val lat: Float, val lon: Float) : Event()
        class ShowSightDetails(val guid: String) : Event()
    }
}