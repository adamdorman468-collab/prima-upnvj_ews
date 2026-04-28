package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.AcademicPredictionInput
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.UserProfile
import com.fik.upnvj.ews.ui.main.AuthMode
import com.fik.upnvj.ews.ui.main.PredictionInputKeys
import com.fik.upnvj.ews.ui.main.PredictionUiState
import com.fik.upnvj.ews.ui.main.SignupInputKeys
import com.fik.upnvj.ews.ui.components.PrimaryButton
import com.fik.upnvj.ews.ui.components.ProgressCard
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.components.StatCard
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.GhostGray
import com.fik.upnvj.ews.ui.theme.MistGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SuccessGreen
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
fun LoginScreen(
    authMode: AuthMode,
    authErrorMessage: String?,
    signupErrors: Map<String, String>,
    onShowLogin: () -> Unit,
    onShowSignup: () -> Unit,
    onLogin: (String, String) -> Unit,
    onSignup: (String, String, String, String, String, String, String) -> Unit
) {
    var nim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var signupNim by remember { mutableStateOf("") }
    var faculty by remember { mutableStateOf("") }
    var studyProgram by remember { mutableStateOf("") }
    var cohort by remember { mutableStateOf("") }
    var signupPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
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
            text = if (authMode == AuthMode.Signup) "Buat Profil Prima" else "Masuk ke Prima",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = if (authMode == AuthMode.Signup) {
                "Data profil disimpan lokal di perangkat ini."
            } else {
                "Masuk dengan NIM dan password profil lokal."
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 28.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (authMode == AuthMode.Signup) {
            PrimaTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nama",
                keyboardType = KeyboardType.Text,
                errorText = signupErrors[SignupInputKeys.NAME]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = signupNim,
                onValueChange = { signupNim = it },
                label = "NIM",
                keyboardType = KeyboardType.Number,
                errorText = signupErrors[SignupInputKeys.NIM]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = faculty,
                onValueChange = { faculty = it },
                label = "Fakultas",
                keyboardType = KeyboardType.Text,
                errorText = signupErrors[SignupInputKeys.FACULTY]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = studyProgram,
                onValueChange = { studyProgram = it },
                label = "Program Studi",
                keyboardType = KeyboardType.Text,
                errorText = signupErrors[SignupInputKeys.STUDY_PROGRAM]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = cohort,
                onValueChange = { cohort = it },
                label = "Angkatan",
                keyboardType = KeyboardType.Number,
                errorText = signupErrors[SignupInputKeys.COHORT]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = signupPassword,
                onValueChange = { signupPassword = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                errorText = signupErrors[SignupInputKeys.PASSWORD]
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi Password",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                errorText = signupErrors[SignupInputKeys.CONFIRM_PASSWORD]
            )
        } else {
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
        }
        authErrorMessage?.let { message ->
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = RiskRed.copy(alpha = 0.12f),
                contentColor = RiskRed
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = if (authMode == AuthMode.Signup) "Daftar" else "Masuk",
            onClick = {
                if (authMode == AuthMode.Signup) {
                    onSignup(name, signupNim, faculty, studyProgram, cohort, signupPassword, confirmPassword)
                } else {
                    onLogin(nim, password)
                }
            }
        )
        TextButton(
            onClick = if (authMode == AuthMode.Signup) onShowLogin else onShowSignup,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = if (authMode == AuthMode.Signup) {
                    "Sudah punya profil? Masuk"
                } else {
                    "Belum punya profil? Daftar"
                }
            )
        }
    }
}

@Composable
private fun PrimaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    errorText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        isError = errorText != null,
        supportingText = errorText?.let { message -> { Text(message) } },
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
    profile: UserProfile,
    dashboard: DashboardSummary,
    onOpenWarning: () -> Unit,
    onOpenProgress: () -> Unit,
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
            text = if (dashboard.hasPrediction) {
                "Ringkasan prediksi terakhir tersimpan lokal."
            } else {
                "Belum ada prediksi akademik tersimpan."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (dashboard.hasPrediction) {
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
                        RiskBadge(severity = dashboard.severity)
                    }
                    Text(
                        text = "%.1f%% confidence".format(dashboard.confidence.coerceIn(0.0, 100.0)),
                        style = MaterialTheme.typography.headlineSmall,
                        color = DarkTeal
                    )
                    LinearProgressIndicator(
                        progress = { (dashboard.confidence / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth(),
                        color = ElectricTeal,
                        trackColor = MistGray,
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = dashboard.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            EmptyStateCard(
                title = "Prediksi belum tersedia",
                message = "Buka tab Warning dan masukkan IPS, SKS, serta jenis kelamin untuk menjalankan model."
            )
        }
        Text("Aksi Cepat", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickActionCard("Lihat Warning", "Penyebab risiko", onOpenWarning, Modifier.weight(1f))
            QuickActionCard("Cek Progress", "Grafik IPS", onOpenProgress, Modifier.weight(1f))
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
private fun EmptyStateCard(
    title: String,
    message: String
) {
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
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun WarningScreen(
    prediction: PredictionUiState,
    onIps1Change: (String) -> Unit,
    onIps2Change: (String) -> Unit,
    onIps3Change: (String) -> Unit,
    onIps4Change: (String) -> Unit,
    onSksChange: (String) -> Unit,
    onGenderChange: (Int) -> Unit,
    onSubmit: () -> Unit,
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
            text = "Prediksi Kelulusan",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Masukkan data akademik sampai semester 4 untuk menjalankan model EWS.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SectionCard(title = "IPS Semester") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaTextField(
                    value = prediction.ips1,
                    onValueChange = onIps1Change,
                    label = "IPS 1",
                    keyboardType = KeyboardType.Decimal,
                    errorText = prediction.validationErrors[PredictionInputKeys.IPS1],
                    modifier = Modifier.weight(1f)
                )
                PrimaTextField(
                    value = prediction.ips2,
                    onValueChange = onIps2Change,
                    label = "IPS 2",
                    keyboardType = KeyboardType.Decimal,
                    errorText = prediction.validationErrors[PredictionInputKeys.IPS2],
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaTextField(
                    value = prediction.ips3,
                    onValueChange = onIps3Change,
                    label = "IPS 3",
                    keyboardType = KeyboardType.Decimal,
                    errorText = prediction.validationErrors[PredictionInputKeys.IPS3],
                    modifier = Modifier.weight(1f)
                )
                PrimaTextField(
                    value = prediction.ips4,
                    onValueChange = onIps4Change,
                    label = "IPS 4",
                    keyboardType = KeyboardType.Decimal,
                    errorText = prediction.validationErrors[PredictionInputKeys.IPS4],
                    modifier = Modifier.weight(1f)
                )
            }
        }
        SectionCard(title = "Data Mahasiswa") {
            PrimaTextField(
                value = prediction.sks,
                onValueChange = onSksChange,
                label = "Total SKS Kumulatif",
                keyboardType = KeyboardType.Number,
                errorText = prediction.validationErrors[PredictionInputKeys.SKS]
            )
            GenderSelector(
                selectedGender = prediction.gender,
                errorText = prediction.validationErrors[PredictionInputKeys.GENDER],
                onGenderChange = onGenderChange
            )
        }
        prediction.errorMessage?.let { message ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = RiskRed.copy(alpha = 0.12f),
                contentColor = RiskRed
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        PrimaryButton(
            text = if (prediction.isLoading) "Menganalisis..." else "Prediksi",
            onClick = onSubmit,
            enabled = !prediction.isLoading
        )
        prediction.result?.let { result ->
            PredictionResultCard(
                prediction = result.prediction,
                confidence = result.confidence,
                status = result.status,
                message = result.message
            )
        }
    }
}

@Composable
private fun PrimaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    modifier: Modifier,
    errorText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        singleLine = true,
        isError = errorText != null,
        supportingText = errorText?.let { message -> { Text(message) } },
        shape = MaterialTheme.shapes.small,
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
private fun GenderSelector(
    selectedGender: Int?,
    errorText: String?,
    onGenderChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Jenis Kelamin",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GenderOption(
                label = "Perempuan",
                value = 0,
                selectedGender = selectedGender,
                onGenderChange = onGenderChange,
                modifier = Modifier.weight(1f)
            )
            GenderOption(
                label = "Laki-laki",
                value = 1,
                selectedGender = selectedGender,
                onGenderChange = onGenderChange,
                modifier = Modifier.weight(1f)
            )
        }
        errorText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun GenderOption(
    label: String,
    value: Int,
    selectedGender: Int?,
    onGenderChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = selectedGender == value
    Surface(
        modifier = modifier.clickable { onGenderChange(value) },
        shape = MaterialTheme.shapes.small,
        color = if (selected) ElectricTeal.copy(alpha = 0.16f) else GhostGray,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = { onGenderChange(value) })
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun PredictionResultCard(
    prediction: Int,
    confidence: Double,
    status: String,
    message: String
) {
    val isSafe = prediction == 1
    val severity = if (isSafe) RiskSeverity.Low else RiskSeverity.High
    val statusLabel = if (isSafe) "Aman / On Track" else "Risiko Tinggi"
    val recommendation = if (isSafe) {
        "Pertahankan stabilitas IPS, selesaikan SKS inti tepat waktu, dan mulai rencanakan mata kuliah akhir."
    } else {
        "Segera jadwalkan konsultasi PA, cek mata kuliah prasyarat, dan susun target perbaikan IPS semester berikutnya."
    }

    ProgressCard(
        title = "Hasil Model",
        value = "%.1f%%".format(confidence.coerceIn(0.0, 100.0))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(statusLabel, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Status API: $status",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            RiskBadge(severity = severity)
        }
        LinearProgressIndicator(
            progress = { (confidence / 100.0).toFloat().coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth(),
            color = if (isSafe) SuccessGreen else RiskRed,
            trackColor = MistGray,
            strokeCap = StrokeCap.Round
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = recommendation,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!dashboard.hasPrediction) {
            EmptyStateCard(
                title = "Progress belum tersedia",
                message = "Jalankan prediksi pertama untuk melihat grafik IPS semester 1-4."
            )
        } else {
            ProgressCard(
                title = "SKS Tersimpan",
                value = "${dashboard.completedCredits} SKS"
            ) {
                Text(
                    text = "Total SKS dari input prediksi terakhir.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ProgressCard(title = "IPS per Semester", value = "4 semester") {
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
                            text = "IPS %.2f".format(entry.ips),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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
        }
        latestPrediction?.let { prediction ->
            SectionCard(title = "Prediksi Terakhir") {
                ProfileRow("Status API", prediction.result.status)
                ProfileRow("Risiko", if (prediction.result.prediction == 1) "Rendah" else "Tinggi")
                ProfileRow("Confidence", "%.1f%%".format(prediction.result.confidence.coerceIn(0.0, 100.0)))
                ProfileRow("SKS", prediction.input.sks.toString())
                ProfileRow("Rerata IPS", "%.2f".format(prediction.input.averageIps))
            }
        } ?: EmptyStateCard(
            title = "Belum ada prediksi",
            message = "Ringkasan profil akan memuat hasil prediksi setelah model dijalankan."
        )
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
        LoginScreen(
            authMode = AuthMode.Signup,
            authErrorMessage = null,
            signupErrors = emptyMap(),
            onShowLogin = {},
            onShowSignup = {},
            onLogin = { _, _ -> },
            onSignup = { _, _, _, _, _, _, _ -> }
        )
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

@Preview(showBackground = true)
@Composable
private fun WarningScreenPreview() {
    PrimaTheme {
        WarningScreen(
            prediction = PredictionUiState(
                ips1 = "3.42",
                ips2 = "3.31",
                ips3 = "3.18",
                ips4 = "3.08",
                sks = "84",
                gender = 1,
                result = PredictResponse(
                    status = "success",
                    prediction = 1,
                    confidence = 86.4,
                    message = "Analisis FastAPI berhasil dilakukan"
                )
            ),
            onIps1Change = {},
            onIps2Change = {},
            onIps3Change = {},
            onIps4Change = {},
            onSksChange = {},
            onGenderChange = {},
            onSubmit = {}
        )
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

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    PrimaTheme {
        ProfileScreen(profile = previewProfile, latestPrediction = previewLatestPrediction, onLogout = {})
    }
}

private val previewProfile = UserProfile(
    name = "Mahasiswa",
    nim = "2410512000",
    faculty = "Fakultas Ilmu Komputer",
    studyProgram = "Sistem Informasi",
    cohort = "2024",
    password = "local"
)

private val previewLatestPrediction = LatestPrediction(
    input = AcademicPredictionInput(
        ips1 = 3.42,
        ips2 = 3.31,
        ips3 = 3.18,
        ips4 = 3.08,
        sks = 84,
        gender = 1
    ),
    result = PredictResponse(
        status = "success",
        prediction = 1,
        confidence = 86.4,
        message = "Analisis FastAPI berhasil dilakukan"
    )
)

private val previewDashboard = DashboardSummary(
    hasPrediction = true,
    status = previewLatestPrediction.result.status,
    riskLabel = RiskSeverity.Low.label,
    severity = RiskSeverity.Low,
    confidence = previewLatestPrediction.result.confidence,
    completedCredits = previewLatestPrediction.input.sks,
    averageIps = previewLatestPrediction.input.averageIps,
    message = previewLatestPrediction.result.message
)
