package com.kvest.pamatky.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val sightsRepository: SightsRepository
) : ViewModel() {
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

    sealed class Event {
        object RefreshFailed : Event()
    }
}