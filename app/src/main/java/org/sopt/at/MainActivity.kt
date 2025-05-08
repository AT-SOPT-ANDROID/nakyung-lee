package org.sopt.at

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.sopt.at.ui.TvingApp
import org.sopt.at.ui.theme.TvingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TvingApp()
                }
            }
        }
    }
}