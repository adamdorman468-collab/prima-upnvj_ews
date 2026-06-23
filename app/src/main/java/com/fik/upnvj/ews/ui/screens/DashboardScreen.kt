package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.UserProfile
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.ui.components.PrimaryButton
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.components.StatCard
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SuccessGreen
import com.fik.upnvj.ews.ui.theme.SurfaceTint
import com.fik.upnvj.ews.ui.theme.WarningAmber

@Composable
fun DashboardScreen(
    profile: UserProfile,
    dashboard: DashboardSummary,
    onOpenWarning: () -> Unit,
    onOpenProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = when (dashboard.severity) {
        RiskSeverity.Low -> SuccessGreen
        RiskSeverity.Medium -> WarningAmber
        RiskSeverity.High -> RiskRed
    }
    val cardBg = accentColor.copy(alpha = 0.08f)
    val contentColor = if (dashboard.severity == RiskSeverity.Low) DarkTeal else accentColor

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Halo, ${profile.name}",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = if (dashboard.hasPrediction) {
                    "Ringkasan prediksi terakhir tersimpan lokal."
                } else {
                    "Belum ada prediksi akademik tersimpan."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (dashboard.hasPrediction) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Status Risiko",
                            style = MaterialTheme.typography.titleMedium,
                            color = contentColor
                        )
                        RiskBadge(severity = dashboard.severity)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                        Text(
                            text = "%.1f%%".format(dashboard.riskPercentage),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "estimasi risiko",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    LinearProgressIndicator(
                        progress = { (dashboard.riskPercentage / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = accentColor,
                        trackColor = accentColor.copy(alpha = 0.15f),
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = dashboard.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    label = "Rerata IPS",
                    value = "%.2f".format(dashboard.averageIps),
                    supportingText = "Semester 1-4",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "SKS",
                    value = "${dashboard.completedCredits}",
                    supportingText = "Kumulatif",
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                "Aksi Cepat",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionCard("Lihat Warning", "Penyebab risiko", onOpenWarning, Modifier.weight(1f))
                QuickActionCard("Cek Progress", "Grafik IPS", onOpenProgress, Modifier.weight(1f))
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = SurfaceTint),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Mulai Prediksi",
                        style = MaterialTheme.typography.titleLarge,
                        color = DarkTeal
                    )
                    Text(
                        text = "Masukkan data IPS, SKS, dan jenis kelamin di tab Warning untuk menjalankan model prediksi kelulusan.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkTeal.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PrimaryButton(
                        text = "Buka Warning",
                        onClick = onOpenWarning
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelLarge,
                color = DarkTeal
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    PrimaTheme {
        DashboardScreen(
            profile = previewProfile,
            dashboard = previewDashboard,
            onOpenWarning = {},
            onOpenProgress = {}
        )
    }
}
