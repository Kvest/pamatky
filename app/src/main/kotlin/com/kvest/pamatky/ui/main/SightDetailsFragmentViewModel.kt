package com.kvest.pamatky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.storage.entity.SightEntity
import com.kvest.pamatky.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class SightDetailsFragmentViewModel(
    guid: String,
    private val sightsRepository: SightsRepository
) : ViewModel(), SightPhotoItemHandler {
    private lateinit var sight: SightEntity

    private val _sightName = MutableLiveData<String>()
    val sightName: LiveData<String>
        get() = _sightName
    private val _photos = MutableLiveData<List<String>>()
    val photos: LiveData<List<String>>
        get() = _photos

    val showGalleryEvent = SingleLiveEvent<ShowGalleryEventData>()

    init {
        viewModelScope.launch {
            sight = sightsRepository.getSight(guid)

            _sightName.value = sight.name
            _photos.value = sight.allPhotos
        }
    }

    override fun onSelect(photo: String) {
        if (::sight.isInitialized) {
            val photos = sight.allPhotos
            val position = photos.indexOf(photo)

            showGalleryEvent.value = ShowGalleryEventData(photos, position)
        }
    }

    class ShowGalleryEventData(val photos: List<String>, val position: Int)
}