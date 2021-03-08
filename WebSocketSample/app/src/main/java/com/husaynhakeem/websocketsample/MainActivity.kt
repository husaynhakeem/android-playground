package com.husaynhakeem.websocketsample

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.husaynhakeem.websocketsample.network.BtcWebSocket.State.*
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BTC price ticker view
        val priceTickerView = findViewById<TickerView>(R.id.priceTickerView)
        priceTickerView.setCharacterLists(TickerUtils.provideNumberList())

        // BTC loading progress bar
        val priceLoader = findViewById<ProgressBar>(R.id.priceLoader)

        // Connection state TextView
        val connectionStateTextView = findViewById<TextView>(R.id.connectionStateTextView)

        // ViewMode setup
        val factory = MainViewModel.Factory()
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.state.observe(this, { state ->
            when (state) {
                is Connecting -> connectionStateTextView.setText(R.string.connection_state_connecting)
                is Connected -> connectionStateTextView.setText(R.string.connection_state_connected)
                is Price -> {
                    // Hide price loader
                    priceLoader.visibility = View.GONE

                    // The price can only be loaded in a connected state, in case a configuration
                    // change occurs, the connection state's text will be blank, the line below
                    // ensures the state is reflected correctly
                    connectionStateTextView.setText(R.string.connection_state_connected)

                    // Update the ticker's value with an animation
                    priceTickerView.text = state.value.toString()
                }
                is Disconnecting -> connectionStateTextView.setText(R.string.connection_state_disconnecting)
                is Disconnected -> connectionStateTextView.setText(R.string.connection_state_disconnected)
                is Error -> Toast.makeText(this, state.cause, Toast.LENGTH_LONG).show()
            }
        })
    }
}
