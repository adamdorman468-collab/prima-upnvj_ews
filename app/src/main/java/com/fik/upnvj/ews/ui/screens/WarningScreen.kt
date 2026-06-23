package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.ui.main.PredictionInputKeys
import com.fik.upnvj.ews.ui.main.PredictionUiState
import com.fik.upnvj.ews.ui.components.PrimaryButton
import com.fik.upnvj.ews.ui.components.PrimaTextField
import com.fik.upnvj.ews.ui.components.SectionCard
import com.fik.upnvj.ews.ui.components.RiskBadge
import com.fik.upnvj.ews.ui.theme.ElectricTeal
import com.fik.upnvj.ews.ui.theme.MistGray
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.fik.upnvj.ews.ui.theme.SuccessGreen
import com.fik.upnvj.ews.ui.theme.White

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
                riskPercentage = result.riskPercentage,
                message = result.message
            )
        }
    }
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

@Composable
private fun PredictionResultCard(
    prediction: Int,
    riskPercentage: Double,
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
