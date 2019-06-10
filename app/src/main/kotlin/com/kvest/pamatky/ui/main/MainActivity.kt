package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kvest.pamatky.R
import com.kvest.pamatky.ext.observe
import com.kvest.pamatky.ext.replaceFragment
import com.kvest.pamatky.ext.showOnMap
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val mapFragment by lazy(LazyThreadSafetyMode.NONE) { SightsMapFragment.newInstance() }
    private val listFragment by lazy(LazyThreadSafetyMode.NONE) { SightsListFragment.newInstance() }
    private var mapItem: MenuItem? = null
    private var listItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        showList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        mapItem = menu.findItem(R.id.map)
        listItem = menu.findItem(R.id.list)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.map -> {
                showMap()
                return true
            }
            R.id.list -> {
                showList()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        observe(viewModel.events, ::onEvent)
    }

    private fun onEvent(event: MainViewModel.Event?) {
        when(event) {
            MainViewModel.Event.RefreshFailed -> Snackbar.make(container, R.string.failed_refresh_sights, Snackbar.LENGTH_SHORT).show()
            is MainViewModel.Event.ShowSightOnMap -> showOnMap(event.lat, event.lon)
            is MainViewModel.Event.ShowSightDetails -> Unit//TODO()
        }
    }

    private fun showMap() {
        replaceFragment(R.id.container, mapFragment)

        mapItem?.isVisible = false
        listItem?.isVisible = true
    }

    private fun showList() {
        replaceFragment(R.id.container, listFragment)

        mapItem?.isVisible = true
        listItem?.isVisible = false
    }
}