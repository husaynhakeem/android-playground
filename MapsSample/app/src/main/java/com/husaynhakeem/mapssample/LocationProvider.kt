package com.husaynhakeem.mapssample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng

class LocationProvider(private val lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    // Callback to receive location updates
    private lateinit var listener: Listener

    // Request that contains parameters for location updates (e.g. updates frequency)
    private lateinit var locationRequest: LocationRequest

    // Allows interacting with the fused location provider
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(listener.getActivity())
    }

    // Callback for when the fused location provider provides a new location
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            val position = result?.lastLocation ?: return
            listener.onLocationUpdated(LatLng(position.latitude, position.longitude))
        }
    }

    // True if the location settings were verified and are ok, false otherwise.
    private var areLocationSettingsOk = false

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        listener = lifecycleOwner as? Listener ?: error("LifecycleOwner must implement Listener")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        tryRegisterLocationUpdates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        unregisterLocationUpdates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    /**
     * Method to be called after the location permission has been granted. This allows to fetch for
     * the user's latest location, and kick-start setting up registering for location updates.
     */
    fun init() {
        getInitialLocation()
        tryRegisterLocationUpdates()
    }

    /**
     * Method to be called after an issue with the location settings has been fixed. An example of
     * an issue is the location setting being turned off.
     */
    fun onLocationSettingsChecked() {
        registerLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun getInitialLocation() {
        fusedLocationProviderClient
            .lastLocation
            .addOnSuccessListener(listener.getActivity()) { location ->
                val position = location ?: return@addOnSuccessListener
                listener.onInitialLocation(LatLng(position.latitude, position.longitude))
            }
    }

    /**
     * Checks if the location settings are ok. If they are, register for location settings.
     * Otherwise, try to fix the issue then retry.
     */
    private fun tryRegisterLocationUpdates() {
        if (areLocationSettingsOk) {
            registerLocationUpdates()
            return
        }

        locationRequest = LocationRequest().apply {
            interval = LOCATION_UPDATES_RATE
            fastestInterval = LOCATION_UPDATES_MAX_RATE
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationSettingRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        LocationServices
            .getSettingsClient(listener.getActivity())
            .checkLocationSettings(locationSettingRequest)
            .addOnSuccessListener {
                areLocationSettingsOk = true
                registerLocationUpdates()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                            listener.getActivity(),
                            REQUEST_CODE_CHECK_SETTINGS
                        )
                    } catch (sendException: IntentSender.SendIntentException) {
                        Log.e(TAG, "startResolutionForResult resulted in an error", sendException)
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun registerLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun unregisterLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    interface Listener {
        fun getActivity(): Activity
        fun onInitialLocation(position: LatLng)
        fun onLocationUpdated(position: LatLng)
    }

    companion object {
        private const val TAG = "LocationUpdatesObserver"
        private const val LOCATION_UPDATES_RATE = 10_000L
        private const val LOCATION_UPDATES_MAX_RATE = 5_000L
        const val REQUEST_CODE_CHECK_SETTINGS = 23
    }
}