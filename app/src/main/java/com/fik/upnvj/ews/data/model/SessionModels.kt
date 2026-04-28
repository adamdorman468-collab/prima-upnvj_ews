package com.fik.upnvj.ews.data.model

data class UserProfile(
    val name: String,
    val nim: String,
    val faculty: String,
    val studyProgram: String,
    val cohort: String,
    val password: String
)

data class AcademicPredictionInput(
    val ips1: Double,
    val ips2: Double,
    val ips3: Double,
    val ips4: Double,
    val sks: Int,
    val gender: Int
) {
    val averageIps: Double
        get() = (ips1 + ips2 + ips3 + ips4) / 4.0
}

data class LatestPrediction(
    val input: AcademicPredictionInput,
    val result: PredictResponse
)
