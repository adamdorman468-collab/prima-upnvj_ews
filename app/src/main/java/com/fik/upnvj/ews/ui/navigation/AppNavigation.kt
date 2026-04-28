package com.fik.upnvj.ews.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.fik.upnvj.ews.ui.theme.PrimaTheme

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: PrimaViewModel
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
                authMode = uiState.authMode,
                authErrorMessage = uiState.authErrorMessage,
                signupErrors = uiState.signupErrors,
                onShowLogin = viewModel::showLogin,
                onShowSignup = viewModel::showSignup,
                onLogin = { nim, password ->
                    if (viewModel.login(nim, password)) {
                        navController.navigate(PrimaRoute.Dashboard.route) {
                            popUpTo(PrimaRoute.Login.route) { inclusive = true }
                        }
                    }
                },
                onSignup = { name, nim, faculty, studyProgram, cohort, password, confirmPassword ->
                    if (viewModel.signup(name, nim, faculty, studyProgram, cohort, password, confirmPassword)) {
                        navController.navigate(PrimaRoute.Dashboard.route) {
                            popUpTo(PrimaRoute.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(PrimaRoute.Dashboard.route) {
            RequireLoggedIn(uiState.isLoggedIn, navController)
            MainScaffold(navController = navController, title = "Dashboard") { modifier ->
                uiState.profile?.let { profile ->
                    DashboardScreen(
                        profile = profile,
                        dashboard = uiState.dashboard,
                        onOpenWarning = { navController.navigate(PrimaRoute.Warning.route) },
                        onOpenProgress = { navController.navigate(PrimaRoute.Progress.route) },
                        modifier = modifier
                    )
                }
            }
        }
        composable(PrimaRoute.Warning.route) {
            RequireLoggedIn(uiState.isLoggedIn, navController)
            MainScaffold(navController = navController, title = "Warning") { modifier ->
                WarningScreen(
                    prediction = uiState.prediction,
                    onIps1Change = viewModel::updateIps1,
                    onIps2Change = viewModel::updateIps2,
                    onIps3Change = viewModel::updateIps3,
                    onIps4Change = viewModel::updateIps4,
                    onSksChange = viewModel::updateSks,
                    onGenderChange = viewModel::updateGender,
                    onSubmit = viewModel::submitPrediction,
                    modifier = modifier
                )
            }
        }
        composable(PrimaRoute.Progress.route) {
            RequireLoggedIn(uiState.isLoggedIn, navController)
            MainScaffold(navController = navController, title = "Progress") { modifier ->
                ProgressScreen(
                    progress = uiState.progress,
                    dashboard = uiState.dashboard,
                    modifier = modifier
                )
            }
        }
        composable(PrimaRoute.Profile.route) {
            RequireLoggedIn(uiState.isLoggedIn, navController)
            MainScaffold(navController = navController, title = "Profile") { modifier ->
                uiState.profile?.let { profile ->
                    ProfileScreen(
                        profile = profile,
                        latestPrediction = uiState.latestPrediction,
                        onLogout = {
                            viewModel.logout()
                            navController.navigate(PrimaRoute.Login.route) {
                                popUpTo(PrimaRoute.Dashboard.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun RequireLoggedIn(
    isLoggedIn: Boolean,
    navController: NavHostController
) {
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(PrimaRoute.Login.route) {
                launchSingleTop = true
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

@Preview(showBackground = true)
@Composable
private fun PrimaTopBarPreview() {
    PrimaTheme {
        PrimaTopBar(title = "Dashboard")
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaBottomNavPreview() {
    PrimaTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { PrimaBottomNav(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = PrimaRoute.Dashboard.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(PrimaRoute.Dashboard.route) { }
                composable(PrimaRoute.Progress.route) { }
                composable(PrimaRoute.Warning.route) { }
                composable(PrimaRoute.Profile.route) { }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScaffoldPreview() {
    PrimaTheme {
        val navController = rememberNavController()
        Column {
            NavHost(
                navController = navController,
                startDestination = PrimaRoute.Dashboard.route,
                modifier = Modifier.height(1.dp)
            ) {
                composable(PrimaRoute.Dashboard.route) { }
                composable(PrimaRoute.Progress.route) { }
                composable(PrimaRoute.Warning.route) { }
                composable(PrimaRoute.Profile.route) { }
            }
        }
        MainScaffold(
            navController = navController,
            title = "Dashboard"
        ) { modifier ->
            Text(
                text = "Preview konten dashboard",
                modifier = modifier
            )
        }
    }
}
