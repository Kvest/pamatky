package com.kvest.pamatky.ui.main

import androidx.lifecycle.*
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.utils.SingleLiveEvent
import kotlinx.coroutines.launch

private const val MIN_SEARCH_SIZE = 3

class MainViewModel(
    private val sightsRepository: SightsRepository
) : ViewModel(), MainListHandler {
    val sights: LiveData<List<BasicSight>> by lazy(LazyThreadSafetyMode.NONE) {
        SightsLiveData(
            sightsRepository.getBasicSightsList(),
            searchText
        )
    }
    val events by lazy(LazyThreadSafetyMode.NONE) { SingleLiveEvent<Event>() }
    private val searchText = MutableLiveData<String>()

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

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
    }

    private class SightsLiveData(sights: LiveData<List<BasicSight>>, search: LiveData<String>) : MediatorLiveData<List<BasicSight>>() {
        private var items: List<BasicSight>? = null
        private var searchText: String? = null

        init {
            addSource(sights) {
                items = it
                update()

            }
            addSource(search) {
                searchText = it
                update()
            }
        }

        fun update() {
            val skipSearch = (searchText?.length ?: 0) < MIN_SEARCH_SIZE
            value = items
                ?.filter { skipSearch || it.name.contains(searchText.orEmpty(), true) }
                ?.sortedBy(BasicSight::name)
        }
    }

    sealed class Event {
        object RefreshFailed : Event()
        class ShowSightOnMap(val lat: Float, val lon: Float) : Event()
        class ShowSightDetails(val guid: String) : Event()
    }
}