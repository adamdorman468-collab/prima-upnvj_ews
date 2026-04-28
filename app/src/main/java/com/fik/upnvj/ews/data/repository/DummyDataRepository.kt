package com.fik.upnvj.ews.data.repository

import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.ProfileInfo
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.model.WarningSummary

class DummyDataRepository {

    fun getProfile(): ProfileInfo = ProfileInfo(
        name = "Rafael",
        nim = "2410512082",
        faculty = "Fakultas Ilmu Komputer",
        studyProgram = "Sistem Informasi",
        cohort = "2024",
        gpa = 3.25
    )

    fun getDashboardSummary(): DashboardSummary = DashboardSummary(
        gpa = 3.25,
        completedCredits = 106,
        totalCredits = 144,
        riskLabel = "Risiko Sedang",
        graduationProbability = 78,
        nextAction = "Konsultasi akademik dan stabilkan IPS semester ini."
    )

    fun getWarningSummary(): WarningSummary = WarningSummary(
        graduationProbability = 78,
        riskLabel = "Risiko Sedang",
        severity = RiskSeverity.Medium,
        reasons = listOf(
            "IPS semester 4 turun dibanding dua semester sebelumnya.",
            "Total SKS masih sedikit di bawah lintasan ideal semester berjalan.",
            "Beberapa mata kuliah prasyarat perlu diselesaikan tepat waktu."
        ),
        recommendation = "Prioritaskan pengambilan SKS inti, jadwalkan konsultasi PA, dan pertahankan IPS minimal 3.20."
    )

    fun getProgressHistory(): List<ProgressEntry> = listOf(
        ProgressEntry(semester = 1, ips = 3.42, credits = 20),
        ProgressEntry(semester = 2, ips = 3.31, credits = 22),
        ProgressEntry(semester = 3, ips = 3.18, credits = 22),
        ProgressEntry(semester = 4, ips = 3.08, credits = 20),
        ProgressEntry(semester = 5, ips = 3.27, credits = 22)
    )
}
