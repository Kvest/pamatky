package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import com.kvest.pamatky.R
import com.kvest.pamatky.ext.*
import com.kvest.pamatky.ui.gallery.GalleryActivity
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

        setSupportActionBar(toolbar)

        initViewModel()

        showList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        mapItem = menu.findItem(R.id.map)
        listItem = menu.findItem(R.id.list)

        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        initSearchView(searchView)

        return true
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onSearchTextChanged(newText)
                return true
            }
        })
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
        when (event) {
            MainViewModel.Event.RefreshFailed -> Snackbar.make(container, R.string.failed_refresh_sights, Snackbar.LENGTH_SHORT).show()
            is MainViewModel.Event.ShowSightOnMap -> showOnMap(event.lat, event.lon)
            is MainViewModel.Event.ShowSightInWaze -> showInWaze(event.lat, event.lon)
            is MainViewModel.Event.ShowGallery -> GalleryActivity.start(this, event.photos.toTypedArray())
            is MainViewModel.Event.PhoneCall -> onPhoneCall(event.guid, event.phoneNumbers)
            is MainViewModel.Event.ShowSite -> openWebPage(event.url)
            is MainViewModel.Event.ShowFacebook -> openFBPage(event.url)
            is MainViewModel.Event.ShowInstagram -> openWebPage(event.url)
        }
    }

    private fun onPhoneCall(guid: String, phoneNumbers: List<String>) {
        if (phoneNumbers.size > 1) {
            val targetView = findViewById<View>(R.id.container).findViewWithTag<View>(guid)
            val popup = PopupMenu(this, targetView)

            val menu = popup.menu
            phoneNumbers.forEach { phoneNumber ->
                menu.add(phoneNumber)
            }

            popup.setOnMenuItemClickListener{ menuItem ->
                val selectedNumber = menuItem.title.toString()
                dialPhoneNumber(selectedNumber)

                true
            }

            popup.show()
        } else {
            dialPhoneNumber(phoneNumbers.first())
        }
    }

    private fun showMap() {
        replaceFragment(R.id.container, mapFragment, setPrimary = true)

        mapItem?.isVisible = false
        listItem?.isVisible = true
    }

    private fun showList() {
        replaceFragment(R.id.container, listFragment)

        mapItem?.isVisible = true
        listItem?.isVisible = false
    }
}