package com.fik.upnvj.ews.data.model

data class DashboardSummary(
    val hasPrediction: Boolean = false,
    val status: String = "",
    val riskLabel: String = "",
    val severity: RiskSeverity = RiskSeverity.Medium,
    val confidence: Double = 0.0,
    val completedCredits: Int = 0,
    val averageIps: Double = 0.0,
    val message: String = ""
)

data class ProgressEntry(
    val semester: Int,
    val ips: Double
)

enum class RiskSeverity(val label: String) {
    Low("Rendah"),
    Medium("Sedang"),
    High("Tinggi")
}
