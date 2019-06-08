package com.kvest.pamatky.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvest.pamatky.repository.SightsRepository
import kotlinx.coroutines.launch

class TestViewModel(
    private val sightsRepository: SightsRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            val result = sightsRepository.updateSights()
            if (!result) {
                //TODO
            }
        }
    }
}