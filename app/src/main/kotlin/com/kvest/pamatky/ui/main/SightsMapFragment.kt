package com.kvest.pamatky.ui.main

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.kvest.pamatky.ext.observe
import com.kvest.pamatky.storage.dto.BasicSight
import org.koin.android.viewmodel.ext.android.sharedViewModel
import com.google.android.gms.maps.model.MarkerOptions

class SightsMapFragment : SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    companion object {
        fun newInstance() = SightsMapFragment()
    }

    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var map: GoogleMap

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnInfoWindowClickListener(this)

//        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
//            private val view = layoutInflater.inflate(R.layout.popup, null, false)
//
//            override fun getInfoContents(marker: Marker) = null
//
//            override fun getInfoWindow(marker: Marker): View {
//                view.name.text = marker.title
//
//                //TODO how to load image asynchronously? Implement some system which will preload images?
//                runBlocking {
//                    val futureBitmap = Glide.with(view.context)
//                        .asBitmap()
//                        .load(url)
//                        .submit()
//                    val bitmap = withContext(Dispatchers.IO){ futureBitmap.get() }
//                    view.image.setImageBitmap(bitmap)
//                }
//
//                return view
//            }
//        })

        //Center map to Prague
        val pragueLatLng = LatLng(50.089079, 14.424482)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pragueLatLng, 7f))

        initViewModel()
    }

    private fun initViewModel() {
        observe(viewModel.sights, ::onSights)
    }

    private fun onSights(list: List<BasicSight>?) {
        map.clear()

        list?.forEach { sight ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(sight.lat.toDouble(), sight.lon.toDouble()))
                    .title(sight.name)
            )
            marker.tag = sight
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        viewModel.onSightSelected(marker.tag as BasicSight)
    }
}