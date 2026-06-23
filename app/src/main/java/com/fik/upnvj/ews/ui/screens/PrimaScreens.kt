package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.fik.upnvj.ews.ui.components.SecondaryButton
import com.fik.upnvj.ews.ui.components.SectionCard
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.components.StatCard
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.DeepCharcoal
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.GhostGray
import com.fik.upnvj.ews.ui.theme.MistGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SlateGray
import com.fik.upnvj.ews.ui.theme.SuccessGreen
import com.fik.upnvj.ews.ui.theme.SurfaceTint
import com.fik.upnvj.ews.ui.theme.WarningAmber
import com.fik.upnvj.ews.ui.theme.White
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
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Prima logo",
                modifier = Modifier.size(168.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Early Warning System",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
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
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
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
                color = RiskRed.copy(alpha = 0.15f),
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
                },
                color = DarkTeal
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
            focusedContainerColor = White,
            unfocusedContainerColor = White,
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
                colors = CardDefaults.cardColors(containerColor = SurfaceTint),
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
                            color = DarkTeal
                        )
                        RiskBadge(severity = dashboard.severity)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                        Text(
                            text = "%.1f%%".format(dashboard.confidence.coerceIn(0.0, 100.0)),
                            style = MaterialTheme.typography.displayLarge,
                            color = DarkTeal
                        )
                        Text(
                            text = "confidence",
                            style = MaterialTheme.typography.labelMedium,
                            color = DarkTeal.copy(alpha = 0.7f)
                        )
                    }
                    LinearProgressIndicator(
                        progress = { (dashboard.confidence / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = ElectricTeal,
                        trackColor = DarkTeal.copy(alpha = 0.12f),
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = dashboard.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkTeal.copy(alpha = 0.8f)
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
                errorText = prediction.validationErrors[PredictionInputKeys.SKS],
                modifier = Modifier.fillMaxWidth()
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
                color = RiskRed.copy(alpha = 0.15f),
                contentColor = RiskRed
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(
                text = if (prediction.isLoading) "Menganalisis..." else "Prediksi",
                onClick = onSubmit,
                enabled = !prediction.isLoading
            )
            if (prediction.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = ElectricTeal,
                    trackColor = MistGray
                )
            }
        }
        
        prediction.result?.let { result ->
            PredictionResultCard(
                prediction = result.prediction,
                confidence = result.confidence,
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
            focusedContainerColor = White,
            unfocusedContainerColor = White,
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
        modifier = modifier
            .clickable { onGenderChange(value) }
            .then(
                if (selected) Modifier.border(1.5.dp, ElectricTeal, MaterialTheme.shapes.small)
                else Modifier
            ),
        shape = MaterialTheme.shapes.small,
        color = if (selected) ElectricTeal.copy(alpha = 0.20f) else White,
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

fun calculateRiskPercentage(
    prediction: Int,
    confidence: Double
): Double {
    val normalizedConfidence = confidence.coerceIn(0.0, 100.0)
    return if (prediction == 1) {
        100.0 - normalizedConfidence
    } else {
        normalizedConfidence
    }.coerceIn(0.0, 100.0)
}

@Composable
private fun PredictionResultCard(
    prediction: Int,
    confidence: Double,
    message: String
) {
    val isSafe = prediction == 1
    val severity = if (isSafe) RiskSeverity.Low else RiskSeverity.High
    val riskPercentage = calculateRiskPercentage(prediction, confidence)
    val statusLabel = if (isSafe) "Aman / On Track" else "Risiko Tinggi"
    val recommendation = if (isSafe) {
        "Pertahankan stabilitas IPS, selesaikan SKS inti tepat waktu, dan mulai rencanakan mata kuliah akhir."
    } else {
        "Segera jadwalkan konsultasi PA, cek mata kuliah prasyarat, dan susun target perbaikan IPS semester berikutnya."
    }

    val cardBg = if (isSafe) SuccessGreen.copy(alpha = 0.08f) else RiskRed.copy(alpha = 0.08f)
    val accentColor = if (isSafe) SuccessGreen else RiskRed

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
                    "Hasil Prediksi",
                    style = MaterialTheme.typography.titleMedium,
                    color = accentColor
                )
                RiskBadge(severity = severity)
            }
            Text(
                text = "%.1f%%".format(riskPercentage),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = statusLabel,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            LinearProgressIndicator(
                progress = { (riskPercentage / 100.0).toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = accentColor,
                trackColor = accentColor.copy(alpha = 0.15f),
                strokeCap = StrokeCap.Round
            )
            HorizontalDivider(color = accentColor.copy(alpha = 0.15f))
            Text(
                text = recommendation,
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
                                "Confidence",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "%.1f%%".format(prediction.result.confidence.coerceIn(0.0, 100.0)),
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
private fun SplashScreenPreview() {
    PrimaTheme {
        SplashScreen(onFinished = {})
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
private fun LoginScreenLoginPreview() {
    PrimaTheme {
        LoginScreen(
            authMode = AuthMode.Login,
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
                    status = "Sukses",
                    prediction = 1,
                    confidence = 86.4,
                    message = "Analisis berhasil dilakukan"
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
    password = "dummy"
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
        status = "Sukses",
        prediction = 1,
        confidence = 86.4,
        message = "Analisis berhasil dilakukan"
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