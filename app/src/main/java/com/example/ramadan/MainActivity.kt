package com.example.ramadan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ramadan.ui.screens.WelcomeScreen
import com.example.ramadan.ui.theme.RamadanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RamadanTheme {
                WelcomeScreen(
                    onStartClick = {/* navigate to another screen */}
                )
            }
        }
    }
}
