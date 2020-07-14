package com.kvest.pamatky.ui.main

import androidx.lifecycle.*
import com.kvest.pamatky.ext.containsIgnoreDiacritic
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.storage.entity.SightEntity
import com.kvest.pamatky.utils.SingleLiveEvent
import kotlinx.coroutines.launch

private const val MIN_SEARCH_SIZE = 3

class MainViewModel(
    private val sightsRepository: SightsRepository
) : ViewModel(), MainListHandler {
    val sights: LiveData<List<SightEntity>> by lazy(LazyThreadSafetyMode.NONE) {
        SightsLiveData(
            sightsRepository.listenSights(),
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

    override fun onSightSelected(sight: SightEntity) {
        viewModelScope.launch {
            val sightFull = sightsRepository.getSight(sight.guid)

            events.value = Event.ShowGallery(sightFull.allPhotos)
        }
    }

    override fun onShowOnMap(sight: SightEntity) {
        events.value = Event.ShowSightOnMap(sight.lat, sight.lon)
    }

    override fun onShowInWaze(sight: SightEntity) {
        events.value = Event.ShowSightInWaze(sight.lat, sight.lon)
    }

    override fun onCall(sight: SightEntity) {
        sight.phone?.let { phone ->
            val phones = phone.split(',')
            events.value = Event.PhoneCall(sight.guid, phones)
        }
    }

    override fun onShowSite(sight: SightEntity) {
        sight.site?.let { url ->
            events.value = Event.ShowSite(url)
        }
    }

    override fun onShowInstargam(sight: SightEntity) {
        sight.instagram?.let { url ->
            events.value = Event.ShowInstagram(url)
        }
    }

    override fun onShowFacebook(sight: SightEntity) {
        sight.facebook?.let { url ->
            events.value = Event.ShowFacebook(url)
        }
    }

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
    }

    private class SightsLiveData(sights: LiveData<List<SightEntity>>, search: LiveData<String>) : MediatorLiveData<List<SightEntity>>() {
        private var items: List<SightEntity>? = null
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
                ?.filter { skipSearch || it.name.containsIgnoreDiacritic(searchText.orEmpty(), true) }
                ?.sortedBy(SightEntity::name)
        }
    }

    sealed class Event {
        object RefreshFailed : Event()
        class ShowSightOnMap(val lat: Float, val lon: Float) : Event()
        class ShowSightInWaze(val lat: Float, val lon: Float) : Event()
        class ShowGallery(val photos: List<String>) : Event()
        class PhoneCall(val guid: String, val phoneNumbers: List<String>) : Event()
        class ShowSite(val url: String) : Event()
        class ShowFacebook(val url: String) : Event()
        class ShowInstagram(val url: String) : Event()
    }
}