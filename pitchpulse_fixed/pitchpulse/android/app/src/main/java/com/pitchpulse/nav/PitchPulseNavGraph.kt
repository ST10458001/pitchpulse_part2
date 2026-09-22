package com.pitchpulse.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pitchpulse.auth.AuthRepository
import com.pitchpulse.auth.LoginScreen
import com.pitchpulse.auth.RegisterScreen
import com.pitchpulse.home.HomeFeedScreen
import com.pitchpulse.settings.SettingsScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN = "main"
    const val HOME = "home"
    const val SETTINGS = "settings"
}

@Composable
fun PitchPulseNavGraph(authRepository: AuthRepository) {
    val navController = rememberNavController()
    val startDestination = if (authRepository.isLoggedIn()) Routes.MAIN else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.MAIN) {
            MainScreen(authRepository)
        }
    }
}

@Composable
private fun MainScreen(authRepository: AuthRepository) {
    val innerNavController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            val backStackEntry = innerNavController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry.value?.destination?.route

            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Routes.HOME,
                    onClick = {
                        innerNavController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.SETTINGS,
                    onClick = {
                        innerNavController.navigate(Routes.SETTINGS) {
                            popUpTo(Routes.HOME)
                        }
                    },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = innerNavController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) { HomeFeedScreen() }
            composable(Routes.SETTINGS) { SettingsScreen(authRepository) }
        }
    }
}
