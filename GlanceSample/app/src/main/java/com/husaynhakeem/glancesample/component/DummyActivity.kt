package com.husaynhakeem.glancesample.component

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.husaynhakeem.glancesample.ui.theme.GlanceSampleTheme
import com.husaynhakeem.glancesample.widget.actionWidgetKey

class DummyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlanceSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Text(
                        text = "Received data: ${intent.getStringExtra(actionWidgetKey.name)}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}