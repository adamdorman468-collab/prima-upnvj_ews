package com.fik.upnvj.ews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

private val LightColorScheme = lightColorScheme(
    primary = ElectricTeal,
    onPrimary = DeepCharcoal,
    secondary = DarkTeal,
    onSecondary = White,
    background = GhostGray,
    onBackground = DeepCharcoal,
    surface = White,
    onSurface = DeepCharcoal,
    surfaceVariant = MistGray,
    onSurfaceVariant = SlateGray,
    error = RiskRed,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = ElectricTeal,
    onPrimary = DeepCharcoal,
    secondary = ElectricTeal,
    onSecondary = DeepCharcoal,
    background = DeepCharcoal,
    onBackground = GhostGray,
    surface = Color(0xFF1E2324),
    onSurface = GhostGray,
    surfaceVariant = Color(0xFF293033),
    onSurfaceVariant = Color(0xFFC8D1D4),
    error = RiskRed,
    onError = White
)

private val PrimaShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun PrimaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = PrimaTypography,
        shapes = PrimaShapes,
        content = content
    )
}
