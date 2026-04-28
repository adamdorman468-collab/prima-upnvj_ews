package com.fik.upnvj.ews.ui.navigation

sealed class PrimaRoute(val route: String) {
    data object Splash : PrimaRoute("splash")
    data object Login : PrimaRoute("login")
    data object Dashboard : PrimaRoute("dashboard")
    data object EwsInput : PrimaRoute("ews-input")
    data object Warning : PrimaRoute("warning")
    data object Progress : PrimaRoute("progress")
    data object Profile : PrimaRoute("profile")
}

data class PrimaTab(
    val route: PrimaRoute,
    val label: String,
    val iconText: String
)

val PrimaTabs = listOf(
    PrimaTab(PrimaRoute.Dashboard, "Home", "H"),
    PrimaTab(PrimaRoute.Progress, "Progress", "P"),
    PrimaTab(PrimaRoute.Warning, "Warning", "W"),
    PrimaTab(PrimaRoute.Profile, "Profile", "U")
)
