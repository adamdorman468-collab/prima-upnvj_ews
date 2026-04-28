package com.fik.upnvj.ews.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fik.upnvj.ews.ui.components.PrimaBottomNav
import com.fik.upnvj.ews.ui.components.PrimaTopBar
import com.fik.upnvj.ews.ui.main.PrimaViewModel
import com.fik.upnvj.ews.ui.screens.DashboardScreen
import com.fik.upnvj.ews.ui.screens.LoginScreen
import com.fik.upnvj.ews.ui.screens.ProfileScreen
import com.fik.upnvj.ews.ui.screens.ProgressScreen
import com.fik.upnvj.ews.ui.screens.SplashScreen
import com.fik.upnvj.ews.ui.screens.WarningScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: PrimaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = PrimaRoute.Splash.route
    ) {
        composable(PrimaRoute.Splash.route) {
            SplashScreen(
                onFinished = {
                    navController.navigate(PrimaRoute.Login.route) {
                        popUpTo(PrimaRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(PrimaRoute.Login.route) {
            LoginScreen(
                onLogin = { nim, password ->
                    viewModel.login(nim, password)
                    navController.navigate(PrimaRoute.Dashboard.route) {
                        popUpTo(PrimaRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(PrimaRoute.Dashboard.route) {
            MainScaffold(navController = navController, title = "Dashboard") { modifier ->
                DashboardScreen(
                    profile = uiState.profile,
                    dashboard = uiState.dashboard,
                    onOpenWarning = { navController.navigate(PrimaRoute.Warning.route) },
                    onOpenProgress = { navController.navigate(PrimaRoute.Progress.route) },
                    modifier = modifier
                )
            }
        }
        composable(PrimaRoute.Warning.route) {
            MainScaffold(navController = navController, title = "Warning") { modifier ->
                WarningScreen(warning = uiState.warning, modifier = modifier)
            }
        }
        composable(PrimaRoute.Progress.route) {
            MainScaffold(navController = navController, title = "Progress") { modifier ->
                ProgressScreen(
                    progress = uiState.progress,
                    dashboard = uiState.dashboard,
                    modifier = modifier
                )
            }
        }
        composable(PrimaRoute.Profile.route) {
            MainScaffold(navController = navController, title = "Profile") { modifier ->
                ProfileScreen(
                    profile = uiState.profile,
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(PrimaRoute.Login.route) {
                            popUpTo(PrimaRoute.Dashboard.route) { inclusive = true }
                        }
                    },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun MainScaffold(
    navController: NavHostController,
    title: String,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = { PrimaTopBar(title = title) },
        bottomBar = { PrimaBottomNav(navController = navController) }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
