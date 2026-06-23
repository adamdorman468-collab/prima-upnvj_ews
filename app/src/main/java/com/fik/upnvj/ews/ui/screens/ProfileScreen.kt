package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.UserProfile
import com.fik.upnvj.ews.ui.components.SecondaryButton
import com.fik.upnvj.ews.ui.components.SectionCard
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.theme.DeepCharcoal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SuccessGreen

@Composable
fun ProfileScreen(
    profile: UserProfile,
    latestPrediction: LatestPrediction?,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val initials = profile.name
            .split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercase() }
            .joinToString("")
            .ifEmpty { "?" }

        Box(
            modifier = Modifier
                .size(80.dp)
                .background(ElectricTeal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.headlineLarge,
                color = DeepCharcoal
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                profile.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = profile.nim,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        SectionCard(title = "Informasi Akademik") {
            ProfileRow("Fakultas", profile.faculty)
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            ProfileRow("Program Studi", profile.studyProgram)
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            ProfileRow("Angkatan", profile.cohort)
        }

        latestPrediction?.let { prediction ->
            val isSafe = prediction.result.prediction == 1
            val severity = if (isSafe) RiskSeverity.Low else RiskSeverity.High
            val accentColor = if (isSafe) SuccessGreen else RiskRed

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = accentColor.copy(alpha = 0.08f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Prediksi Terakhir",
                            style = MaterialTheme.typography.titleMedium,
                            color = accentColor
                        )
                        RiskBadge(severity = severity)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column {
                            Text(
                                "IPS Rata-rata",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "%.2f".format(prediction.input.averageIps),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Column {
                            Text(
                                "SKS",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "${prediction.input.sks}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Column {
                            Text(
                                "Estimasi Risiko",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "%.1f%%".format(prediction.result.riskPercentage),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        } ?: Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Belum ada prediksi", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Ringkasan profil akan memuat hasil prediksi setelah model dijalankan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        SecondaryButton(
            text = "Keluar",
            onClick = onLogout,
            contentColor = RiskRed
        )
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    PrimaTheme {
        ProfileScreen(profile = previewProfile, latestPrediction = previewLatestPrediction, onLogout = {})
    }
}
