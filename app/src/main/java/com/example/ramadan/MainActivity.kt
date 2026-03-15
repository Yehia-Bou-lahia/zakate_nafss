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


import com.example.ramadan.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        org.osmdroid.config.Configuration.getInstance().load(this, sharedPref)
        
        val isFirstLaunch = sharedPref.getBoolean("is_first_launch", true)
        val startDest = if (isFirstLaunch) Routes.WELCOME else Routes.QURAN

        enableEdgeToEdge()
        setContent {
            RamadanTheme {
                //AdhkarScreen()
                //DhikrScreen()
                NavGraph(
                    startDestination = startDest,
                    onSetupComplete = {
                        sharedPref.edit().putBoolean("is_first_launch", false).apply()
                    }
                )
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
