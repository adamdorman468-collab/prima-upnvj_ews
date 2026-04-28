package com.fik.upnvj.ews.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
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

private val PrimaShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun PrimaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = PrimaTypography,
        shapes = PrimaShapes,
        content = content
    )
}
