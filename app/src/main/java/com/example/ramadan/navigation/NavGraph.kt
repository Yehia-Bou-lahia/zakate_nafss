package com.example.ramadan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ramadan.ui.screens.OnboardingScreen
import com.example.ramadan.ui.screens.ProfileSetupScreen
import com.example.ramadan.ui.screens.WelcomeScreen

// ── أسماء الصفحات ─────────────────────────────────
object Routes {
    const val WELCOME    = "welcome"
    const val ONBOARDING = "onboarding"
    const val PROFILE_SETUP = "profile_setup"
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
        composable(Routes.PROFILE_SETUP) {
            ProfileSetupScreen(
                onContinueClick = {
                    // لاحقاً نربطها بالصفحة الرئيسية
                }
            )
        }
    }
}