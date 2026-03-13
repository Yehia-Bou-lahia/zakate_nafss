package com.example.ramadan

import android.os.Bundle
import android.widget.AbsListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ramadan.navigation.NavGraph
import com.example.ramadan.ui.screens.AdhkarScreen
import com.example.ramadan.ui.screens.DashboardScreen
import com.example.ramadan.ui.screens.DhikrScreen
import com.example.ramadan.ui.screens.WelcomeScreen
import com.example.ramadan.ui.theme.RamadanTheme
import com.example.ramadan.ui.screens.OnboardingScreen
import com.example.ramadan.ui.screens.PrayerNotificationScreen
import com.example.ramadan.ui.screens.QuranScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().load(
            this,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        )
        enableEdgeToEdge()
        setContent {
            RamadanTheme {
                //AdhkarScreen()
                DhikrScreen()
                //NavGraph()
                //QuranScreen()
                //DashboardScreen()
                //WelcomeScreen()
                //OnboardingScreen()
                //PrayerNotificationScreen()
                //ProfileSetupScreen ()
            }
        }
    }
}
