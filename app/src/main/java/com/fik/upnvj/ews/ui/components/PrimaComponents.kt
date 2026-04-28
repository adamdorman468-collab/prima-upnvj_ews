package com.fik.upnvj.ews.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.ui.navigation.PrimaRoute
import com.fik.upnvj.ews.ui.navigation.PrimaTabs
import com.fik.upnvj.ews.ui.theme.DeepCharcoal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.GhostGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SuccessGreen
import com.fik.upnvj.ews.ui.theme.WarningAmber

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = ElectricTeal,
            contentColor = DeepCharcoal
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProgressCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            content()
        }
    }
}

@Composable
fun PrimaTopBar(title: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun PrimaBottomNav(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        PrimaTabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == tab.route.route
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = if (selected) ElectricTeal else GhostGray
                    ) {
                        Text(
                            text = tab.iconText,
                            modifier = Modifier.padding(top = 5.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelMedium,
                            color = DeepCharcoal
                        )
                    }
                },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
fun RiskBadge(
    severity: RiskSeverity,
    modifier: Modifier = Modifier
) {
    val badgeColor = when (severity) {
        RiskSeverity.Low -> SuccessGreen
        RiskSeverity.Medium -> WarningAmber
        RiskSeverity.High -> RiskRed
    }
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = badgeColor.copy(alpha = 0.16f),
        contentColor = badgeColor
    ) {
        Text(
            text = severity.label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaComponentsPreview() {
    PrimaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(text = "Masuk", onClick = {})
            StatCard(label = "IPK", value = "3.25", supportingText = "Stabil")
            ProgressCard(title = "SKS", value = "106/144") {
                Text("Progress akademik berjalan.")
            }
            RiskBadge(severity = RiskSeverity.Medium)
        }
    }
}
