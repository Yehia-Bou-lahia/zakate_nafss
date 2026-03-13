package com.example.ramadan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ramadan.ui.screens.AdhkarScreen
import com.example.ramadan.ui.screens.DashboardScreen
import com.example.ramadan.ui.screens.DhikrScreen
import com.example.ramadan.ui.screens.OnboardingScreen
import com.example.ramadan.ui.screens.ProfileSetupScreen
import com.example.ramadan.ui.screens.QuranScreen
import com.example.ramadan.ui.screens.WelcomeScreen

// ── أسماء الصفحات ─────────────────────────────────
object Routes {
    const val WELCOME    = "welcome"
    const val ONBOARDING = "onboarding"
    const val PROFILE_SETUP = "profile_setup"
    const val  DASHBOARD = "dashboard"
    const val QURAN = "quran"
    const val DHIKR = "dhikr"
    const val ADHKAR = "adhkar/{type}"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        // صفحة الترحيب
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Routes.ONBOARDING)
                }
            )
        }

        // صفحة اختيار المسار
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onBackEvent = {
                    navController.popBackStack()
                },
                onContinueEvent = {
                    navController.navigate(Routes.PROFILE_SETUP)
                }
            )
        }
        // static page
        composable(Routes.PROFILE_SETUP) {
            ProfileSetupScreen(
                /*onBackEvent = {
                    navController.popBackStack()
                },*/
                onContinueClick = {
                    navController.navigate(Routes.QURAN)
                }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(onNavigate = { navController.navigate(it) })
        }

        composable(Routes.QURAN) {
            QuranScreen(onNavigate = { navController.navigate(it) })
        }

        composable(Routes.DHIKR) {
            DhikrScreen(onNavigate = { navController.navigate(it) })
        }

        composable("adhkar/{type}") { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "sabah"
            AdhkarScreen(
                type   = type,
                onBack = { navController.popBackStack() }
            )
        }

    }
}