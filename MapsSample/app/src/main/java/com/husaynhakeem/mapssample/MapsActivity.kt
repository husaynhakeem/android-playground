package com.husaynhakeem.mapssample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener,
    LocationProvider.Listener {

    private lateinit var map: GoogleMap
    private lateinit var locationProvider: LocationProvider
    private var lastMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize the location provider, which will provide location updates
        locationProvider = LocationProvider(this)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationProvider.REQUEST_CODE_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            locationProvider.onLocationSettingsChecked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id in MAP_TYPES.keys) {
            map.mapType = MAP_TYPES[id] ?: error("Unknown map type $id")
            return true
        }
        return onOptionsItemSelected(item)
    }

    // region OnMarkerClickListener
    override fun onMarkerClick(marker: Marker?) = false
    // endregion OnMarkerClickListener

    // region OnMapReadyCallback
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Enable zoom controls on the map
        map.uiSettings.isZoomControlsEnabled = true

        // Set callback for when marker is tapped
        map.setOnMarkerClickListener(this)

        // Request the required location permission, then move the map to the current location
        map.tryMoveToCurrentLocation()
    }

    private fun GoogleMap.tryMoveToCurrentLocation() {
        if (arePermissionsGranted()) {
            moveToCurrentLocation()
            locationProvider.init()
        } else {
            requestPermissions(this)
        }
    }

    private fun arePermissionsGranted(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions(map: GoogleMap) {
        val launcher = registerForActivityResult(RequestMultiplePermissions()) {
            map.tryMoveToCurrentLocation()
        }
        launcher.launch(PERMISSIONS)
    }

    @SuppressLint("MissingPermission")
    private fun GoogleMap.moveToCurrentLocation() {
        // Draws a blue dot on the user's current location
        isMyLocationEnabled = true
    }

    private fun GoogleMap.placeMarker(position: LatLng) {
        // Remove last marker
        lastMarker?.remove()

        // Add new marker
        val markerOptions = MarkerOptions().position(position).title(position.toAddress())
        lastMarker = addMarker(markerOptions)
    }

    private fun LatLng.toAddress(): String {
        val addresses = try {
            val geocoder = Geocoder(this@MapsActivity)
            geocoder.getFromLocation(latitude, longitude, 1)
        } catch (exception: IOException) {
            Log.e(TAG, "An error occurred while converting $this into an address", exception)
            null
        }

        if (addresses.isNullOrEmpty()) {
            return ""
        }
        return addresses.first().toLiteral()
    }

    private fun Address.toLiteral(): String = buildString {
        for (i in 0..maxAddressLineIndex) {
            val addressLine = getAddressLine(i)
            append(if (i == 0) addressLine else "\n $addressLine")
        }
    }
    // endregion OnMapReadyCallback

    // region LocationUpdatesObserver.Listener
    override fun getActivity() = this

    override fun onInitialLocation(position: LatLng) {
        map.placeMarker(position)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM))
    }

    override fun onLocationUpdated(position: LatLng) {
        map.placeMarker(position)
    }
    // endregion LocationUpdatesObserver.Listener

    companion object {
        private const val TAG = "MapsActivity"
        private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private const val DEFAULT_ZOOM = 12F
        private val MAP_TYPES = mapOf(
            R.id.normal to MAP_TYPE_NORMAL,
            R.id.satellite to MAP_TYPE_SATELLITE,
            R.id.terrain to MAP_TYPE_TERRAIN,
            R.id.hybrid to MAP_TYPE_HYBRID
        )
    }
}