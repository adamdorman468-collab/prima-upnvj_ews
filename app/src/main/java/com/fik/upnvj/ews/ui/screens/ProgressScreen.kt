package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.ui.components.SectionCard
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.MistGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SlateGray
import com.fik.upnvj.ews.ui.theme.SuccessGreen
import com.fik.upnvj.ews.ui.theme.SurfaceTint
import com.fik.upnvj.ews.ui.theme.WarningAmber

@Composable
fun ProgressScreen(
    progress: List<ProgressEntry>,
    dashboard: DashboardSummary,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (!dashboard.hasPrediction) {
            Card(
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
                    Text("Progress belum tersedia", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Jalankan prediksi pertama untuk melihat grafik IPS semester 1-4.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "${dashboard.completedCredits} SKS",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Total SKS kumulatif dari prediksi terakhir",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "IPS per Semester",
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkTeal
                    )
                    IpsBarChart(entries = progress)
                }
            }
            
            SectionCard(title = "Detail Semester") {
                progress.forEachIndexed { index, entry ->
                    if (index > 0) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (entry.ips >= 3.0) SuccessGreen else WarningAmber,
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                "Semester ${entry.semester}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (index > 0) {
                                val delta = entry.ips - progress[index - 1].ips
                                val deltaText = if (delta >= 0) "+%.2f".format(delta) else "%.2f".format(delta)
                                val deltaColor = if (delta >= 0) SuccessGreen else RiskRed
                                Text(
                                    text = deltaText,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = deltaColor
                                )
                            }
                            Text(
                                text = "%.2f".format(entry.ips),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            if (progress.size >= 2) {
                val first = progress.first().ips
                val last = progress.last().ips
                val delta = last - first
                val trend = if (delta > 0.05) {
                    "IPS meningkat %.2f dari semester ${progress.first().semester} ke ${progress.last().semester}."
                } else if (delta < -0.05) {
                    "IPS menurun %.2f dari semester ${progress.first().semester} ke ${progress.last().semester}."
                } else {
                    "IPS stabil dari semester ${progress.first().semester} ke ${progress.last().semester}."
                }
                val formattedTrend = trend.format(kotlin.math.abs(delta))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = SurfaceTint
                ) {
                    Text(
                        text = formattedTrend,
                        modifier = Modifier.padding(14.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkTeal
                    )
                }
            }
        }
    }
}

@Composable
private fun IpsBarChart(entries: List<ProgressEntry>) {
    val barColor = ElectricTeal
    val lowBarColor = WarningAmber
    val textColor = SlateGray
    val refLineColor = RiskRed.copy(alpha = 0.3f)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            if (entries.isEmpty()) return@Canvas
            val topPadding = 24.dp.toPx()
            val gap = 16.dp.toPx()
            val chartHeight = size.height - topPadding - 4.dp.toPx()
            val barWidth = (size.width - gap * (entries.size + 1)) / entries.size

            val refY = topPadding + chartHeight * (1f - (2.0f / 4.0f))
            drawLine(
                color = refLineColor,
                start = Offset(0f, refY),
                end = Offset(size.width, refY),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 4.dp.toPx()))
            )

            entries.forEachIndexed { index, entry ->
                val normalized = (entry.ips / 4.0).toFloat()
                val barHeight = chartHeight * normalized
                val left = gap + index * (barWidth + gap)
                val barTop = topPadding + chartHeight - barHeight
                val color = if (entry.ips >= 3.0) barColor else lowBarColor

                drawRoundRect(
                    color = MistGray,
                    topLeft = Offset(left, topPadding),
                    size = Size(barWidth, chartHeight),
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
                drawRoundRect(
                    color = color,
                    topLeft = Offset(left, barTop),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            entries.forEach { entry ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "%.2f".format(entry.ips),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (entry.ips >= 3.0) DarkTeal else WarningAmber
                    )
                    Text(
                        text = "S${entry.semester}",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressScreenPreview() {
    PrimaTheme {
        ProgressScreen(
            progress = listOf(
                ProgressEntry(1, 3.42),
                ProgressEntry(2, 3.31),
                ProgressEntry(3, 3.18),
                ProgressEntry(4, 3.08)
            ),
            dashboard = previewDashboard
        )
    }
}
