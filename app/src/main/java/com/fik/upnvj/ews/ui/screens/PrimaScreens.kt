package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.ProfileInfo
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.WarningSummary
import com.fik.upnvj.ews.data.repository.DummyDataRepository
import com.fik.upnvj.ews.ui.components.PrimaryButton
import com.fik.upnvj.ews.ui.components.ProgressCard
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.components.StatCard
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.DeepCharcoal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.GhostGray
import com.fik.upnvj.ews.ui.theme.MistGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.WarningAmber
import com.upnvj.prima.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Prima logo",
                modifier = Modifier.size(116.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Prima",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Early Warning System",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var nim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_round),
            contentDescription = "Prima logo",
            modifier = Modifier.size(92.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Masuk ke Prima",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Pantau progres akademik dengan data demo.",
            modifier = Modifier.padding(top = 8.dp, bottom = 28.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        PrimaTextField(
            value = nim,
            onValueChange = { nim = it },
            label = "NIM",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(12.dp))
        PrimaTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(text = "Masuk", onClick = { onLogin(nim, password) })
    }
}

@Composable
private fun PrimaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = GhostGray,
            unfocusedContainerColor = GhostGray,
            focusedBorderColor = ElectricTeal,
            unfocusedBorderColor = MistGray
        )
    )
}

@Composable
fun DashboardScreen(
    profile: ProfileInfo,
    dashboard: DashboardSummary,
    onOpenWarning: () -> Unit,
    onOpenProgress: () -> Unit,
    onOpenEwsInput: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Halo, ${profile.name}",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Semester berjalan membutuhkan fokus pada SKS inti dan stabilitas IPS.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                label = "IPK",
                value = "%.2f".format(dashboard.gpa),
                supportingText = "Kumulatif",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "SKS",
                value = "${dashboard.completedCredits}",
                supportingText = "dari ${dashboard.totalCredits}",
                modifier = Modifier.weight(1f)
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Status Risiko", style = MaterialTheme.typography.titleMedium)
                    RiskBadge(severity = RiskSeverity.Medium)
                }
                Text(
                    text = "${dashboard.graduationProbability}% peluang lulus tepat waktu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DarkTeal
                )
                LinearProgressIndicator(
                    progress = { dashboard.graduationProbability / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = ElectricTeal,
                    trackColor = MistGray,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = dashboard.nextAction,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text("Aksi Cepat", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard("Lihat Warning", "Penyebab risiko", onOpenWarning, Modifier.weight(1f))
            QuickActionCard("Cek Progress", "Grafik IPS", onOpenProgress, Modifier.weight(1f))
        }
        PrimaryButton(text = "Isi Form EWS", onClick = onOpenEwsInput)
    }
}

@Composable
fun EwsInputScreen(modifier: Modifier = Modifier) {
    var ips1 by remember { mutableStateOf("") }
    var ips2 by remember { mutableStateOf("") }
    var ips3 by remember { mutableStateOf("") }
    var ips4 by remember { mutableStateOf("") }
    var sks by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Laki-laki") }
    var showResult by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "EWS Akademik FIK UPNVJ",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        PrimaTextField(value = ips1, onValueChange = { ips1 = it }, label = "IPS Semester 1", keyboardType = KeyboardType.Number)
        PrimaTextField(value = ips2, onValueChange = { ips2 = it }, label = "IPS Semester 2", keyboardType = KeyboardType.Number)
        PrimaTextField(value = ips3, onValueChange = { ips3 = it }, label = "IPS Semester 3", keyboardType = KeyboardType.Number)
        PrimaTextField(value = ips4, onValueChange = { ips4 = it }, label = "IPS Semester 4", keyboardType = KeyboardType.Number)
        PrimaTextField(value = sks, onValueChange = { sks = it }, label = "Total SKS", keyboardType = KeyboardType.Number)

        Text(text = "Jenis Kelamin", style = MaterialTheme.typography.bodyMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == "Laki-laki",
                    onClick = { selectedGender = "Laki-laki" },
                    colors = RadioButtonDefaults.colors(selectedColor = ElectricTeal)
                )
                Text(text = "Laki-laki", style = MaterialTheme.typography.bodyMedium)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == "Perempuan",
                    onClick = { selectedGender = "Perempuan" },
                    colors = RadioButtonDefaults.colors(selectedColor = ElectricTeal)
                )
                Text(text = "Perempuan", style = MaterialTheme.typography.bodyMedium)
            }
        }

        PrimaryButton(
            text = "PREDIKSI SEKARANG",
            onClick = { showResult = true }
        )

        if (showResult) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Hasil Prediksi",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Risiko: Sedang",
                        style = MaterialTheme.typography.headlineSmall,
                        color = WarningAmber
                    )
                    Text(
                        text = "Rekomendasi: Konsultasi akademik dan fokus pada peningkatan IPS.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun WarningScreen(
    warning: WarningSummary,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProgressCard(
            title = "Peluang Lulus",
            value = "${warning.graduationProbability}%"
        ) {
            LinearProgressIndicator(
                progress = { warning.graduationProbability / 100f },
                modifier = Modifier.fillMaxWidth(),
                color = ElectricTeal,
                trackColor = MistGray,
                strokeCap = StrokeCap.Round
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(warning.riskLabel, style = MaterialTheme.typography.headlineSmall)
                RiskBadge(severity = warning.severity)
            }
        }
        SectionCard(title = "Alasan") {
            warning.reasons.forEach { reason ->
                Text(
                    text = "- $reason",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        SectionCard(title = "Rekomendasi") {
            Text(
                text = warning.recommendation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProgressScreen(
    progress: List<ProgressEntry>,
    dashboard: DashboardSummary,
    modifier: Modifier = Modifier
) {
    val creditProgress = dashboard.completedCredits / dashboard.totalCredits.toFloat()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProgressCard(
            title = "SKS Terkumpul",
            value = "${dashboard.completedCredits}/${dashboard.totalCredits}"
        ) {
            LinearProgressIndicator(
                progress = { creditProgress },
                modifier = Modifier.fillMaxWidth(),
                color = ElectricTeal,
                trackColor = MistGray,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = "${(creditProgress * 100).toInt()}% dari kebutuhan kelulusan.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        ProgressCard(title = "IPS per Semester", value = "5 semester") {
            IpsBarChart(entries = progress)
        }
        SectionCard(title = "Ringkasan") {
            progress.forEach { entry ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Semester ${entry.semester}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "IPS %.2f | ${entry.credits} SKS".format(entry.ips),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun IpsBarChart(entries: List<ProgressEntry>) {
    val barColor = ElectricTeal
    val markerColor = WarningAmber
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            if (entries.isEmpty()) return@Canvas
            val gap = 16.dp.toPx()
            val chartHeight = size.height - 28.dp.toPx()
            val barWidth = (size.width - gap * (entries.size + 1)) / entries.size
            entries.forEachIndexed { index, entry ->
                val normalized = (entry.ips / 4.0).toFloat()
                val barHeight = chartHeight * normalized
                val left = gap + index * (barWidth + gap)
                drawRoundRect(
                    color = MistGray,
                    topLeft = Offset(left, 0f),
                    size = Size(barWidth, chartHeight),
                    cornerRadius = CornerRadius(18.dp.toPx(), 18.dp.toPx())
                )
                drawRoundRect(
                    color = if (entry.ips >= 3.2) barColor else markerColor,
                    topLeft = Offset(left, chartHeight - barHeight),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(18.dp.toPx(), 18.dp.toPx())
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            entries.forEach { entry ->
                Text(
                    text = "S${entry.semester}",
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    profile: ProfileInfo,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_square),
            contentDescription = "Prima logo",
            modifier = Modifier.size(96.dp)
        )
        Text(profile.name, style = MaterialTheme.typography.headlineLarge)
        Text(
            text = profile.nim,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SectionCard(title = "Informasi Akademik") {
            ProfileRow("Fakultas", profile.faculty)
            ProfileRow("Program Studi", profile.studyProgram)
            ProfileRow("Angkatan", profile.cohort)
            ProfileRow("IPK", "%.2f".format(profile.gpa))
        }
        PrimaryButton(text = "Keluar", onClick = onLogout)
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
private fun LoginScreenPreview() {
    PrimaTheme {
        LoginScreen(onLogin = { _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    val repository = DummyDataRepository()
    PrimaTheme {
        DashboardScreen(
            profile = repository.getProfile(),
            dashboard = repository.getDashboardSummary(),
            onOpenWarning = {},
            onOpenProgress = {},
            onOpenEwsInput = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WarningScreenPreview() {
    PrimaTheme {
        WarningScreen(warning = DummyDataRepository().getWarningSummary())
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressScreenPreview() {
    val repository = DummyDataRepository()
    PrimaTheme {
        ProgressScreen(
            progress = repository.getProgressHistory(),
            dashboard = repository.getDashboardSummary()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    PrimaTheme {
        ProfileScreen(profile = DummyDataRepository().getProfile(), onLogout = {})
    }
}
