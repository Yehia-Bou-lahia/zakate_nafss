package com.example.ramadan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
fun NavGraph(
    startDestination: String = Routes.WELCOME,
    onSetupComplete: () -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(400)) },
        exitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(400)) },
        popEnterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(400)) },
        popExitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(400)) }
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
                    onSetupComplete()
                    navController.navigate(Routes.QURAN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(Routes.QURAN) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }

        composable(Routes.QURAN) {
            QuranScreen(onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(Routes.QURAN) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }

        composable(Routes.DHIKR) {
            DhikrScreen(onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(Routes.QURAN) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }

        composable(
            route = "adhkar/{type}",
            arguments = listOf(navArgument("type") { defaultValue = "sabah" })
        ) { backStackEntry ->
            AdhkarScreen(
                type   = backStackEntry.arguments?.getString("type") ?: "sabah",
                onBack = { navController.popBackStack() }
            )
        }

    }
}