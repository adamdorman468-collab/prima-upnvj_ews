package com.fik.upnvj.ews.data.model

data class DashboardSummary(
    val gpa: Double,
    val completedCredits: Int,
    val totalCredits: Int,
    val riskLabel: String,
    val graduationProbability: Int,
    val nextAction: String
)

data class WarningSummary(
    val graduationProbability: Int,
    val riskLabel: String,
    val severity: RiskSeverity,
    val reasons: List<String>,
    val recommendation: String
)

data class ProgressEntry(
    val semester: Int,
    val ips: Double,
    val credits: Int
)

data class ProfileInfo(
    val name: String,
    val nim: String,
    val faculty: String,
    val studyProgram: String,
    val cohort: String,
    val gpa: Double
)

enum class RiskSeverity(val label: String) {
    Low("Rendah"),
    Medium("Sedang"),
    High("Tinggi")
}
