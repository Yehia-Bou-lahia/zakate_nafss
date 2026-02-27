package com.example.ramadan

import android.os.Bundle
import android.widget.AbsListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ramadan.ui.screens.WelcomeScreen
import com.example.ramadan.ui.theme.RamadanTheme
import com.example.ramadan.ui.screens.OnboardingScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RamadanTheme {
                /*WelcomeScreen(
                    onStartClick = {*//* navigate to another screen *//*}
                )*/
                OnboardingScreen()

            }
        }
    }
}
