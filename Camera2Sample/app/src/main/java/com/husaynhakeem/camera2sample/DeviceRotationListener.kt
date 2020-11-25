package com.husaynhakeem.camera2sample

import android.content.Context
import android.view.OrientationEventListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Observes the device's physical rotation and continually communicates it to its listener. The
 * emitted rotation value is either 0, 90, 180 or 270.
 *
 * [DeviceRotationListener] starts observing the device's rotation when its associated
 * [lifecycleOwner] starts, stops observing when the [lifecycleOwner] stops and detaches itself
 * from the [lifecycleOwner] when it's destroyed.
 */
abstract class DeviceRotationListener(
    private val lifecycleOwner: LifecycleOwner,
    context: Context
) : LifecycleObserver {

    private var currentRotation = 0
    private val rotationListener = object : OrientationEventListener(context.applicationContext) {
        override fun onOrientationChanged(orientation: Int) {
            if (orientation == ORIENTATION_UNKNOWN) {
                return
            }

            val newRotation = when {
                orientation <= 45 -> 0
                orientation <= 135 -> 90
                orientation <= 225 -> 180
                orientation <= 315 -> 270
                else -> 0
            }

            if (newRotation != currentRotation) {
                currentRotation = newRotation
                onRotationChanged(newRotation)
            }
        }
    }

    init {
        @Suppress("LeakingThis")
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onActive() {
        rotationListener.enable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onInactive() {
        rotationListener.disable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    /** Returns the device's current physical rotation, which is either 0, 90, 180 or 270. */
    fun getRotation(): Int {
        return currentRotation
    }

    /** Invoked when the device's physical orientation changes. */
    abstract fun onRotationChanged(rotation: Int)
}