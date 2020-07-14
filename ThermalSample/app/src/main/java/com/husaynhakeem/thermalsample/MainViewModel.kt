package com.husaynhakeem.thermalsample

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        val viewState = when {
            !isThermalApiSupported() -> State.ThermalApiNotSupported
            isThermalServiceRunning(application) -> State.ThermalServiceRunning
            else -> State.ThermalServiceNotRunning
        }
        _state.value = viewState
    }

    private fun isThermalApiSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    @Suppress("DEPRECATION")
    private fun isThermalServiceRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == ThermalService::class.java.name }
    }

    fun startThermalService() {
        val intent = Intent(getContext(), ThermalService::class.java)
        getContext().startService(intent)
        _state.value = State.ThermalServiceRunning
    }

    fun stopThermalService() {
        val intent = Intent(getContext(), ThermalService::class.java)
        getContext().stopService(intent)
        _state.value = State.ThermalServiceNotRunning
    }

    private fun getContext(): Context = getApplication()

    sealed class State {
        object ThermalApiNotSupported : State()
        object ThermalServiceRunning : State()
        object ThermalServiceNotRunning : State()
    }
}