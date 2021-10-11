package com.husaynhakeem.gradledependencyconfigssample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val logger = LoggerProvider.get()
            val loggingInput = findViewById<EditText>(R.id.loggingInputEditText)
            val logButton = findViewById<Button>(R.id.logButton)
            logButton.setOnClickListener {
                val message = loggingInput.text.toString()
                logger.log(message)
            }
        } catch (exception: LoggerImplementationNotFoundException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
        }
    }
}