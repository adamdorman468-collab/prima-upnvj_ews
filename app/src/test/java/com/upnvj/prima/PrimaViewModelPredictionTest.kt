package com.upnvj.prima

import com.fik.upnvj.ews.data.model.AcademicPredictionInput
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.PredictRequest
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.UserProfile
import com.fik.upnvj.ews.data.repository.LocalSessionRepository
import com.fik.upnvj.ews.data.repository.PredictionRepository
import com.fik.upnvj.ews.ui.screens.calculateRiskPercentage
import com.fik.upnvj.ews.ui.main.AuthMode
import com.fik.upnvj.ews.ui.main.PrimaViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class PrimaViewModelPredictionTest {

    @Test
    fun safePredictionConvertsConfidenceToLowRiskPercentage() {
        assertEquals(13.8, calculateRiskPercentage(prediction = 1, confidence = 86.2), 0.001)
    }

    @Test
    fun unsafePredictionUsesConfidenceAsRiskPercentage() {
        assertEquals(86.2, calculateRiskPercentage(prediction = 0, confidence = 86.2), 0.001)
    }

    @Test
    fun riskPercentageIsAlwaysClamped() {
        assertEquals(0.0, calculateRiskPercentage(prediction = 0, confidence = -5.0), 0.0)
        assertEquals(100.0, calculateRiskPercentage(prediction = 0, confidence = 150.0), 0.0)
        assertEquals(0.0, calculateRiskPercentage(prediction = 1, confidence = 120.0), 0.0)
        assertEquals(100.0, calculateRiskPercentage(prediction = 1, confidence = -10.0), 0.0)
    }

    @Test
    fun noStoredUserStartsInSignupLoggedOutState() {
        val viewModel = buildViewModel(sessionRepository = FakeSessionRepository())

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertEquals(AuthMode.Signup, state.authMode)
        assertNull(state.profile)
        assertFalse(state.dashboard.hasPrediction)
        assertTrue(state.progress.isEmpty())
    }

    @Test
    fun signupSavesProfileAndLogsIn() {
        val sessionRepository = FakeSessionRepository()
        val viewModel = buildViewModel(sessionRepository = sessionRepository)

        val didSignup = viewModel.signup(
            name = "Dina",
            nim = "2410512001",
            faculty = "Fakultas Ilmu Komputer",
            studyProgram = "Sistem Informasi",
            cohort = "2024",
            password = "secret",
            confirmPassword = "secret"
        )

        assertTrue(didSignup)
        assertTrue(viewModel.uiState.value.isLoggedIn)
        assertEquals("Dina", viewModel.uiState.value.profile?.name)
        assertEquals("2410512001", sessionRepository.profile?.nim)
    }

    @Test
    fun logoutKeepsStoredProfileButMarksLoggedOut() {
        val sessionRepository = FakeSessionRepository(profile = testProfile)
        val viewModel = buildViewModel(sessionRepository = sessionRepository)

        assertTrue(viewModel.login(testProfile.nim, testProfile.password))
        viewModel.logout()

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertEquals(AuthMode.Login, state.authMode)
        assertEquals(testProfile, state.profile)
        assertEquals(testProfile, sessionRepository.profile)
    }

    @Test
    fun validLoginRestoresAppAccess() {
        val viewModel = buildViewModel(sessionRepository = FakeSessionRepository(profile = testProfile))

        val didLogin = viewModel.login(testProfile.nim, testProfile.password)

        assertTrue(didLogin)
        assertTrue(viewModel.uiState.value.isLoggedIn)
        assertEquals(AuthMode.Login, viewModel.uiState.value.authMode)
        assertNull(viewModel.uiState.value.authErrorMessage)
    }

    @Test
    fun successResponsePersistsPredictionAndUpdatesTabState() {
        val sessionRepository = FakeSessionRepository(profile = testProfile)
        val repository = FakePredictionRepository(
            response = Response.success(
                PredictResponse(
                    status = "success",
                    prediction = 1,
                    confidence = 91.5,
                    message = "Analisis berhasil"
                )
            )
        )
        val viewModel = buildViewModel(repository, sessionRepository)

        fillValidInput(viewModel)
        viewModel.submitPrediction()

        val state = viewModel.uiState.value
        val prediction = state.prediction
        assertEquals(PredictRequest(3.42, 3.31, 3.18, 3.08, 84, 1), repository.lastRequest)
        assertEquals(1, prediction.result?.prediction)
        assertEquals(91.5, prediction.result?.confidence ?: 0.0, 0.0)
        assertEquals(sessionRepository.storedLatestPrediction, state.latestPrediction)
        assertTrue(state.dashboard.hasPrediction)
        assertEquals(84, state.dashboard.completedCredits)
        assertEquals(4, state.progress.size)
        assertNull(prediction.errorMessage)
        assertTrue(prediction.validationErrors.isEmpty())
    }

    @Test
    fun apiErrorResponseUpdatesVisibleErrorState() {
        val errorBody = "server error".toResponseBody("text/plain".toMediaType())
        val repository = FakePredictionRepository(response = Response.error(500, errorBody))
        val viewModel = buildViewModel(repository)

        fillValidInput(viewModel)
        viewModel.submitPrediction()

        val prediction = viewModel.uiState.value.prediction
        assertNull(prediction.result)
        assertNotNull(prediction.errorMessage)
        assertTrue(prediction.errorMessage!!.contains("500"))
    }

    @Test
    fun networkExceptionUpdatesVisibleErrorState() {
        val repository = FakePredictionRepository(exception = IOException("timeout"))
        val viewModel = buildViewModel(repository)

        fillValidInput(viewModel)
        viewModel.submitPrediction()

        val prediction = viewModel.uiState.value.prediction
        assertNull(prediction.result)
        assertNotNull(prediction.errorMessage)
        assertTrue(prediction.errorMessage!!.contains("timeout"))
    }

    @Test
    fun apiErrorDoesNotOverwritePreviousSuccessfulPrediction() {
        val previousPrediction = LatestPrediction(
            input = AcademicPredictionInput(3.5, 3.4, 3.3, 3.2, 82, 0),
            result = PredictResponse(
                status = "success",
                prediction = 1,
                confidence = 88.0,
                message = "Sebelumnya berhasil"
            )
        )
        val sessionRepository = FakeSessionRepository(
            profile = testProfile,
            storedLatestPrediction = previousPrediction
        )
        val repository = FakePredictionRepository(
            response = Response.error(503, "down".toResponseBody("text/plain".toMediaType()))
        )
        val viewModel = buildViewModel(repository, sessionRepository)

        fillValidInput(viewModel)
        viewModel.submitPrediction()

        val state = viewModel.uiState.value
        assertEquals(previousPrediction, sessionRepository.storedLatestPrediction)
        assertEquals(previousPrediction, state.latestPrediction)
        assertEquals(previousPrediction.result, state.prediction.result)
        assertTrue(state.prediction.errorMessage!!.contains("503"))
    }

    private fun buildViewModel(
        predictionRepository: PredictionRepository = FakePredictionRepository(
            response = Response.success(PredictResponse("success", 1, 90.0, "ok"))
        ),
        sessionRepository: FakeSessionRepository = FakeSessionRepository()
    ): PrimaViewModel {
        return PrimaViewModel(
            sessionRepository = sessionRepository,
            predictionRepository = predictionRepository,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    private fun fillValidInput(viewModel: PrimaViewModel) {
        viewModel.updateIps1("3.42")
        viewModel.updateIps2("3.31")
        viewModel.updateIps3("3.18")
        viewModel.updateIps4("3.08")
        viewModel.updateSks("84")
        viewModel.updateGender(1)
    }

    private class FakePredictionRepository(
        private val response: Response<PredictResponse>? = null,
        private val exception: Exception? = null
    ) : PredictionRepository {
        var lastRequest: PredictRequest? = null
            private set

        override suspend fun predict(request: PredictRequest): Response<PredictResponse> {
            lastRequest = request
            exception?.let { throw it }
            return response ?: error("Fake response not configured")
        }
    }

    private class FakeSessionRepository(
        var profile: UserProfile? = null,
        var storedLatestPrediction: LatestPrediction? = null
    ) : LocalSessionRepository {
        override fun getUserProfile(): UserProfile? = profile

        override fun saveUserProfile(profile: UserProfile) {
            this.profile = profile
        }

        override fun getLatestPrediction(): LatestPrediction? = storedLatestPrediction

        override fun saveLatestPrediction(prediction: LatestPrediction) {
            storedLatestPrediction = prediction
        }
    }

    private companion object {
        val testProfile = UserProfile(
            name = "Dina",
            nim = "2410512001",
            faculty = "Fakultas Ilmu Komputer",
            studyProgram = "Sistem Informasi",
            cohort = "2024",
            password = "secret"
        )
    }
}
