package com.husaynhakeem.camera2sample

import android.content.Context
import android.view.OrientationEventListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

abstract class SensorRotationListener(
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

    fun getRotation(): Int {
        return currentRotation
    }

    abstract fun onRotationChanged(rotation: Int)
}