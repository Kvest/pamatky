package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.kvest.pamatky.ext.observe
import com.kvest.pamatky.storage.dto.BasicSight
import org.koin.android.viewmodel.ext.android.sharedViewModel
import com.google.android.gms.maps.model.MarkerOptions
import com.kvest.pamatky.R
import com.google.android.gms.maps.SupportMapFragment
import com.kvest.pamatky.ext.inTransaction

class SightsMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    companion object {
        fun newInstance() = SightsMapFragment()

        private const val DETAILS_FRAGMENT_BACKSTACK_NAME = "details_fragment"
    }

    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sights_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)

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

    override fun onMarkerClick(marker: Marker): Boolean {
        val sight = marker.tag as BasicSight

        childFragmentManager.popBackStack(DETAILS_FRAGMENT_BACKSTACK_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        childFragmentManager.inTransaction {
            val fragment = SightDetailsFragment.newInstance(sight.guid)

            setCustomAnimations(R.anim.enter_up, R.anim.exit_down, R.anim.enter_down, R.anim.exit_up)
            replace(R.id.sightDetailsContainer, fragment)
            addToBackStack(DETAILS_FRAGMENT_BACKSTACK_NAME)
        }

        return true
    }
}