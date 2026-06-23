package com.fik.upnvj.ews.ui.screens

import com.fik.upnvj.ews.data.model.AcademicPredictionInput
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.LatestPrediction
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.UserProfile

internal val previewProfile = UserProfile(
    name = "Mahasiswa",
    nim = "2410512000",
    faculty = "Fakultas Ilmu Komputer",
    studyProgram = "Sistem Informasi",
    cohort = "2024",
    password = "dummy"
)

internal val previewLatestPrediction = LatestPrediction(
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

internal val previewDashboard = DashboardSummary(
    hasPrediction = true,
    status = previewLatestPrediction.result.status,
    riskLabel = RiskSeverity.Low.label,
    severity = RiskSeverity.Low,
    riskPercentage = previewLatestPrediction.result.riskPercentage,
    completedCredits = previewLatestPrediction.input.sks,
    averageIps = previewLatestPrediction.input.averageIps,
    message = previewLatestPrediction.result.message
)
