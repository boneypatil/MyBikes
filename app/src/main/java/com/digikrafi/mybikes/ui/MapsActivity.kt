package com.digikrafi.mybikes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.digikrafi.mybikes.R
import com.digikrafi.mybikes.databinding.ActivityMapsBinding
import com.digikrafi.mybikes.databinding.MainFragmentBinding
import com.digikrafi.mybikes.model.Features
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by rahul.p
 *
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var feature : Features? =null

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        readBundle()
    }

    /**
     *Reading Parsed data
     */
    private fun readBundle() {
        feature = intent.getParcelableExtra(getString(R.string.extra_intent_feature)) as Features?
        binding.itemHolderMapView.feature = feature
    }


    /**
     * Adding all the marker to the map view
     * and getting the camera angle within all the
     * possible marker location view
     *
     */
    private fun loadCarDataOnMapView(features: Features) {
        val builder = LatLngBounds.Builder()
            mMap.addMarker(
                MarkerOptions().position(LatLng(features.geometry?.coordinates?.get(1) ?: 0.0,
                    features.geometry?.coordinates?.get(0) ?: 0.0
                )).title(
                    features.properties?.label
                )
            )
            //the include method will calculate the min and max bound.
            builder.include(
                LatLng(features.geometry?.coordinates?.get(1) ?: 0.0,
                    features.geometry?.coordinates?.get(0) ?: 0.0)

            )


        val bounds = builder.build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.10).toInt() // offset from edges of the map 10% of screen


        val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        mMap.animateCamera(cu)
        moveCameraToTheCarLocation(features)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        feature?.let { loadCarDataOnMapView(it) }
    }

    /**
     * Updating the camera position
     *
     */
    private fun moveCameraToTheCarLocation(features: Features?) {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    features?.geometry?.coordinates?.get(1) ?: 0.0,
                    features?.geometry?.coordinates?.get(0) ?: 0.0
                ), 17.0f
            )
        )
    }
}