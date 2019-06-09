package com.kvest.pamatky.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kvest.pamatky.R
import com.kvest.pamatky.ext.observe
import com.kvest.pamatky.ext.showOnMap
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    private fun initViewModel() {
        observe(viewModel.events, ::onEvent)
    }

    private fun onEvent(event: MainViewModel.Event?) {
        when(event) {
            MainViewModel.Event.RefreshFailed -> Unit//TODO()
            is MainViewModel.Event.ShowSightOnMap -> showOnMap(event.lat, event.lon)
            is MainViewModel.Event.ShowSightDetails -> Unit//TODO()
        }
    }
}