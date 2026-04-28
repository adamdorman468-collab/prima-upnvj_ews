package com.fik.upnvj.ews.data.repository

import android.content.SharedPreferences
import com.fik.upnvj.ews.data.model.AcademicPredictionInput
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.UserProfile

interface LocalSessionRepository {
    fun getUserProfile(): UserProfile?
    fun saveUserProfile(profile: UserProfile)
    fun getLatestPrediction(): LatestPrediction?
    fun saveLatestPrediction(prediction: LatestPrediction)
}

class SharedPreferencesSessionRepository(
    private val preferences: SharedPreferences
) : LocalSessionRepository {

    override fun getUserProfile(): UserProfile? {
        val nim = preferences.getString(KEY_PROFILE_NIM, null) ?: return null
        return UserProfile(
            name = preferences.getString(KEY_PROFILE_NAME, "").orEmpty(),
            nim = nim,
            faculty = preferences.getString(KEY_PROFILE_FACULTY, "").orEmpty(),
            studyProgram = preferences.getString(KEY_PROFILE_STUDY_PROGRAM, "").orEmpty(),
            cohort = preferences.getString(KEY_PROFILE_COHORT, "").orEmpty(),
            password = preferences.getString(KEY_PROFILE_PASSWORD, "").orEmpty()
        )
    }

    override fun saveUserProfile(profile: UserProfile) {
        preferences.edit()
            .putString(KEY_PROFILE_NAME, profile.name)
            .putString(KEY_PROFILE_NIM, profile.nim)
            .putString(KEY_PROFILE_FACULTY, profile.faculty)
            .putString(KEY_PROFILE_STUDY_PROGRAM, profile.studyProgram)
            .putString(KEY_PROFILE_COHORT, profile.cohort)
            .putString(KEY_PROFILE_PASSWORD, profile.password)
            .apply()
    }

    override fun getLatestPrediction(): LatestPrediction? {
        if (!preferences.contains(KEY_PREDICTION_STATUS)) return null
        return LatestPrediction(
            input = AcademicPredictionInput(
                ips1 = preferences.getFloat(KEY_INPUT_IPS1, 0f).toDouble(),
                ips2 = preferences.getFloat(KEY_INPUT_IPS2, 0f).toDouble(),
                ips3 = preferences.getFloat(KEY_INPUT_IPS3, 0f).toDouble(),
                ips4 = preferences.getFloat(KEY_INPUT_IPS4, 0f).toDouble(),
                sks = preferences.getInt(KEY_INPUT_SKS, 0),
                gender = preferences.getInt(KEY_INPUT_GENDER, 0)
            ),
            result = PredictResponse(
                status = preferences.getString(KEY_PREDICTION_STATUS, "").orEmpty(),
                prediction = preferences.getInt(KEY_PREDICTION_VALUE, 0),
                confidence = preferences.getFloat(KEY_PREDICTION_CONFIDENCE, 0f).toDouble(),
                message = preferences.getString(KEY_PREDICTION_MESSAGE, "").orEmpty()
            )
        )
    }

    override fun saveLatestPrediction(prediction: LatestPrediction) {
        preferences.edit()
            .putFloat(KEY_INPUT_IPS1, prediction.input.ips1.toFloat())
            .putFloat(KEY_INPUT_IPS2, prediction.input.ips2.toFloat())
            .putFloat(KEY_INPUT_IPS3, prediction.input.ips3.toFloat())
            .putFloat(KEY_INPUT_IPS4, prediction.input.ips4.toFloat())
            .putInt(KEY_INPUT_SKS, prediction.input.sks)
            .putInt(KEY_INPUT_GENDER, prediction.input.gender)
            .putString(KEY_PREDICTION_STATUS, prediction.result.status)
            .putInt(KEY_PREDICTION_VALUE, prediction.result.prediction)
            .putFloat(KEY_PREDICTION_CONFIDENCE, prediction.result.confidence.toFloat())
            .putString(KEY_PREDICTION_MESSAGE, prediction.result.message)
            .apply()
    }

    private companion object {
        const val KEY_PROFILE_NAME = "profile_name"
        const val KEY_PROFILE_NIM = "profile_nim"
        const val KEY_PROFILE_FACULTY = "profile_faculty"
        const val KEY_PROFILE_STUDY_PROGRAM = "profile_study_program"
        const val KEY_PROFILE_COHORT = "profile_cohort"
        const val KEY_PROFILE_PASSWORD = "profile_password"

        const val KEY_INPUT_IPS1 = "input_ips1"
        const val KEY_INPUT_IPS2 = "input_ips2"
        const val KEY_INPUT_IPS3 = "input_ips3"
        const val KEY_INPUT_IPS4 = "input_ips4"
        const val KEY_INPUT_SKS = "input_sks"
        const val KEY_INPUT_GENDER = "input_gender"
        const val KEY_PREDICTION_STATUS = "prediction_status"
        const val KEY_PREDICTION_VALUE = "prediction_value"
        const val KEY_PREDICTION_CONFIDENCE = "prediction_confidence"
        const val KEY_PREDICTION_MESSAGE = "prediction_message"
    }
}
