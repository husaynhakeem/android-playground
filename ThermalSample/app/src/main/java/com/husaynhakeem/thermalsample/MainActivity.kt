package com.husaynhakeem.thermalsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.husaynhakeem.thermalsample.MainViewModel.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerUiStateListener()
    }

    private fun registerUiStateListener() {
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                State.ThermalApiNotSupported -> {
                    flipper.displayedChild = THERMAL_API_NOT_SUPPORTED
                }
                State.ThermalServiceRunning -> {
                    flipper.displayedChild = THERMAL_API_SUPPORTED
                    startStopThermalService.setText(R.string.stop_thermal_service)
                    startStopThermalService.setOnClickListener {
                        viewModel.stopThermalService()
                    }
                }
                State.ThermalServiceNotRunning -> {
                    flipper.displayedChild = THERMAL_API_SUPPORTED
                    startStopThermalService.setText(R.string.start_thermal_service)
                    startStopThermalService.setOnClickListener {
                        viewModel.startThermalService()
                    }
                }
            }
        })
    }

    companion object {
        private const val THERMAL_API_NOT_SUPPORTED = 0
        private const val THERMAL_API_SUPPORTED = 1
    }
}