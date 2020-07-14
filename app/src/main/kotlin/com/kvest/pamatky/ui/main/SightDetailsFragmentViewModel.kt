package com.kvest.pamatky.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.storage.entity.SightEntity
import com.kvest.pamatky.utils.SingleLiveEvent

class SightDetailsFragmentViewModel(
    guid: String,
    sightsRepository: SightsRepository
) : ViewModel(), SightPhotoItemHandler {
    val sight: LiveData<SightEntity> = sightsRepository.listenSight(guid)
    val showGalleryEvent = SingleLiveEvent<ShowGalleryEventData>()

    override fun onSelect(photo: String) {
        sight.value?.allPhotos?.let { photos ->
            val position = photos.indexOf(photo)

            showGalleryEvent.value = ShowGalleryEventData(photos, position)
        }
    }

    class ShowGalleryEventData(val photos: List<String>, val position: Int)
}