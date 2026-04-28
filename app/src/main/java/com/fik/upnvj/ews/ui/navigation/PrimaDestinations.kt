package com.fik.upnvj.ews.ui.navigation

import androidx.annotation.DrawableRes
import com.upnvj.prima.R

sealed class PrimaRoute(val route: String) {
    data object Splash : PrimaRoute("splash")
    data object Login : PrimaRoute("login")
    data object Dashboard : PrimaRoute("dashboard")
    data object Warning : PrimaRoute("warning")
    data object Progress : PrimaRoute("progress")
    data object Profile : PrimaRoute("profile")
}

data class PrimaTab(
    val route: PrimaRoute,
    val label: String,
    @DrawableRes val iconResId: Int
)

val PrimaTabs = listOf(
    PrimaTab(PrimaRoute.Dashboard, "Home", R.drawable.ic_home),
    PrimaTab(PrimaRoute.Progress, "Progress", R.drawable.ic_progress),
    PrimaTab(PrimaRoute.Warning, "Warning", R.drawable.ic_warning),
    PrimaTab(PrimaRoute.Profile, "Profile", R.drawable.ic_profile)
)
