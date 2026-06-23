package com.fik.upnvj.ews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fik.upnvj.ews.ui.main.AuthMode
import com.fik.upnvj.ews.ui.main.SignupInputKeys
import com.fik.upnvj.ews.ui.components.PrimaryButton
import com.fik.upnvj.ews.ui.components.PrimaTextField
import com.fik.upnvj.ews.ui.theme.DarkTeal
import com.fik.upnvj.ews.ui.theme.PrimaTheme
import com.fik.upnvj.ews.ui.theme.RiskRed
import com.upnvj.prima.R

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

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PrimaTheme {
        LoginScreen(
            authMode = AuthMode.Signup,
            authErrorMessage = null,
            signupErrors = emptyMap<String, String>(),
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
            signupErrors = emptyMap<String, String>(),
            onShowLogin = {},
            onShowSignup = {},
            onLogin = { _, _ -> },
            onSignup = { _, _, _, _, _, _, _ -> }
        )
    }
}
