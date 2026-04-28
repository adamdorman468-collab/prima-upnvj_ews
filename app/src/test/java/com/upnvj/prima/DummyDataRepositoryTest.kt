package com.upnvj.prima

import com.fik.upnvj.ews.data.model.RiskSeverity
import com.fik.upnvj.ews.data.repository.DummyDataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DummyDataRepositoryTest {

    private val repository = DummyDataRepository()

    @Test
    fun defaultProfileMatchesMvpDemoStudent() {
        val profile = repository.getProfile()

        assertEquals("Rafael", profile.name)
        assertEquals("2410512082", profile.nim)
        assertEquals(3.25, profile.gpa, 0.0)
    }

    @Test
    fun warningSummaryReturnsExpectedRiskDefaults() {
        val warning = repository.getWarningSummary()

        assertEquals(78, warning.graduationProbability)
        assertEquals(RiskSeverity.Medium, warning.severity)
        assertTrue(warning.reasons.isNotEmpty())
    }
}
