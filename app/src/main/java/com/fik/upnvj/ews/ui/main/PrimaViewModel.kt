package com.fik.upnvj.ews.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fik.upnvj.ews.data.api.RetrofitClient
import com.fik.upnvj.ews.data.model.AcademicPredictionInput
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.PredictRequest
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.UserProfile
import com.fik.upnvj.ews.data.repository.LocalSessionRepository
import com.fik.upnvj.ews.data.repository.MainRepository
import com.fik.upnvj.ews.data.repository.PredictionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AuthMode {
    Login,
    Signup
}

data class PredictionUiState(
    val ips1: String = "",
    val ips2: String = "",
    val ips3: String = "",
    val ips4: String = "",
    val sks: String = "",
    val gender: Int? = null,
    val isLoading: Boolean = false,
    val result: PredictResponse? = null,
    val validationErrors: Map<String, String> = emptyMap(),
    val errorMessage: String? = null
)

data class PrimaUiState(
    val profile: UserProfile? = null,
    val latestPrediction: LatestPrediction? = null,
    val dashboard: DashboardSummary = DashboardSummary(),
    val progress: List<ProgressEntry> = emptyList(),
    val prediction: PredictionUiState = PredictionUiState(),
    val isLoggedIn: Boolean = false,
    val authMode: AuthMode = AuthMode.Signup,
    val authErrorMessage: String? = null,
    val signupErrors: Map<String, String> = emptyMap()
)

class PrimaViewModel(
    private val sessionRepository: LocalSessionRepository,
    private val predictionRepository: PredictionRepository = MainRepository(RetrofitClient.apiService),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val storedProfile = sessionRepository.getUserProfile()
    private val storedPrediction = sessionRepository.getLatestPrediction()

    private val _uiState = MutableStateFlow(
        PrimaUiState(
            profile = storedProfile,
            latestPrediction = storedPrediction,
            dashboard = buildDashboardSummary(storedPrediction),
            progress = buildProgress(storedPrediction),
            prediction = buildPredictionState(storedPrediction),
            authMode = if (storedProfile == null) AuthMode.Signup else AuthMode.Login
        )
    )
    val uiState: StateFlow<PrimaUiState> = _uiState.asStateFlow()

    fun showLogin() {
        _uiState.update { it.copy(authMode = AuthMode.Login, authErrorMessage = null, signupErrors = emptyMap()) }
    }

    fun showSignup() {
        _uiState.update { it.copy(authMode = AuthMode.Signup, authErrorMessage = null, signupErrors = emptyMap()) }
    }

    fun signup(
        name: String,
        nim: String,
        faculty: String,
        studyProgram: String,
        cohort: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val validation = SignupInputValidator.validate(
            name = name,
            nim = nim,
            faculty = faculty,
            studyProgram = studyProgram,
            cohort = cohort,
            password = password,
            confirmPassword = confirmPassword
        )

        if (!validation.isValid) {
            _uiState.update {
                it.copy(
                    authMode = AuthMode.Signup,
                    signupErrors = validation.errors,
                    authErrorMessage = null
                )
            }
            return false
        }

        val profile = validation.profile!!
        sessionRepository.saveUserProfile(profile)
        _uiState.update {
            it.copy(
                profile = profile,
                isLoggedIn = true,
                authMode = AuthMode.Login,
                signupErrors = emptyMap(),
                authErrorMessage = null
            )
        }
        return true
    }

    fun login(nim: String, password: String): Boolean {
        val profile = _uiState.value.profile ?: sessionRepository.getUserProfile()
        if (profile == null) {
            _uiState.update {
                it.copy(
                    authMode = AuthMode.Signup,
                    authErrorMessage = "Buat profil lokal terlebih dahulu."
                )
            }
            return false
        }

        val isValid = profile.nim == nim.trim() && profile.password == password
        _uiState.update {
            if (isValid) {
                it.copy(
                    profile = profile,
                    isLoggedIn = true,
                    authMode = AuthMode.Login,
                    authErrorMessage = null,
                    signupErrors = emptyMap()
                )
            } else {
                it.copy(
                    profile = profile,
                    isLoggedIn = false,
                    authMode = AuthMode.Login,
                    authErrorMessage = "NIM atau password tidak sesuai."
                )
            }
        }
        return isValid
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isLoggedIn = false,
                authMode = AuthMode.Login,
                authErrorMessage = null,
                signupErrors = emptyMap()
            )
        }
    }

    fun updateIps1(value: String) {
        updatePrediction { it.copy(ips1 = value, validationErrors = it.validationErrors - PredictionInputKeys.IPS1) }
    }

    fun updateIps2(value: String) {
        updatePrediction { it.copy(ips2 = value, validationErrors = it.validationErrors - PredictionInputKeys.IPS2) }
    }

    fun updateIps3(value: String) {
        updatePrediction { it.copy(ips3 = value, validationErrors = it.validationErrors - PredictionInputKeys.IPS3) }
    }

    fun updateIps4(value: String) {
        updatePrediction { it.copy(ips4 = value, validationErrors = it.validationErrors - PredictionInputKeys.IPS4) }
    }

    fun updateSks(value: String) {
        updatePrediction { it.copy(sks = value, validationErrors = it.validationErrors - PredictionInputKeys.SKS) }
    }

    fun updateGender(value: Int) {
        updatePrediction { it.copy(gender = value, validationErrors = it.validationErrors - PredictionInputKeys.GENDER) }
    }

    fun submitPrediction() {
        val predictionState = _uiState.value.prediction
        val validation = PredictionInputValidator.validate(
            ips1 = predictionState.ips1,
            ips2 = predictionState.ips2,
            ips3 = predictionState.ips3,
            ips4 = predictionState.ips4,
            sks = predictionState.sks,
            gender = predictionState.gender
        )

        if (!validation.isValid) {
            updatePrediction {
                it.copy(
                    isLoading = false,
                    validationErrors = validation.errors,
                    errorMessage = null
                )
            }
            return
        }

        val request = validation.request!!
        updatePrediction {
            it.copy(
                isLoading = true,
                validationErrors = emptyMap(),
                errorMessage = null
            )
        }

        viewModelScope.launch(ioDispatcher) {
            try {
                val response = predictionRepository.predict(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        persistSuccessfulPrediction(request, body)
                    } else {
                        updatePrediction { it.copy(isLoading = false, errorMessage = "Respons prediksi kosong.") }
                    }
                } else {
                    updatePrediction {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Gagal mendapatkan prediksi (${response.code()}): ${response.message()}"
                        )
                    }
                }
            } catch (e: Exception) {
                updatePrediction {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Tidak dapat terhubung ke server prediksi: ${e.localizedMessage ?: "kesalahan jaringan"}"
                    )
                }
            }
        }
    }

    private fun persistSuccessfulPrediction(request: PredictRequest, response: PredictResponse) {
        val latestPrediction = LatestPrediction(
            input = AcademicPredictionInput(
                ips1 = request.ips1,
                ips2 = request.ips2,
                ips3 = request.ips3,
                ips4 = request.ips4,
                sks = request.sks,
                gender = request.jk
            ),
            result = response
        )
        sessionRepository.saveLatestPrediction(latestPrediction)
        _uiState.update {
            it.copy(
                latestPrediction = latestPrediction,
                dashboard = buildDashboardSummary(latestPrediction),
                progress = buildProgress(latestPrediction),
                prediction = it.prediction.copy(
                    isLoading = false,
                    result = response,
                    validationErrors = emptyMap(),
                    errorMessage = null
                )
            )
        }
    }

    private fun updatePrediction(transform: (PredictionUiState) -> PredictionUiState) {
        _uiState.update { state ->
            state.copy(prediction = transform(state.prediction))
        }
    }
}

class PrimaViewModelFactory(
    private val sessionRepository: LocalSessionRepository,
    private val predictionRepository: PredictionRepository = MainRepository(RetrofitClient.apiService),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrimaViewModel::class.java)) {
            return PrimaViewModel(
                sessionRepository = sessionRepository,
                predictionRepository = predictionRepository,
                ioDispatcher = ioDispatcher
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

private fun buildPredictionState(latestPrediction: LatestPrediction?): PredictionUiState {
    val input = latestPrediction?.input ?: return PredictionUiState()
    return PredictionUiState(
        ips1 = "%.2f".format(input.ips1),
        ips2 = "%.2f".format(input.ips2),
        ips3 = "%.2f".format(input.ips3),
        ips4 = "%.2f".format(input.ips4),
        sks = input.sks.toString(),
        gender = input.gender,
        result = latestPrediction.result
    )
}

private fun buildDashboardSummary(latestPrediction: LatestPrediction?): DashboardSummary {
    val input = latestPrediction?.input ?: return DashboardSummary()
    val result = latestPrediction.result
    val severity = result.toRiskSeverity()
    return DashboardSummary(
        hasPrediction = true,
        status = result.status,
        riskLabel = severity.label,
        severity = severity,
        confidence = result.confidence,
        completedCredits = input.sks,
        averageIps = input.averageIps,
        message = result.message
    )
}

private fun buildProgress(latestPrediction: LatestPrediction?): List<ProgressEntry> {
    val input = latestPrediction?.input ?: return emptyList()
    return listOf(
        ProgressEntry(semester = 1, ips = input.ips1),
        ProgressEntry(semester = 2, ips = input.ips2),
        ProgressEntry(semester = 3, ips = input.ips3),
        ProgressEntry(semester = 4, ips = input.ips4)
    )
}

private fun PredictResponse.toRiskSeverity(): RiskSeverity {
    return if (prediction == 1) RiskSeverity.Low else RiskSeverity.High
}
