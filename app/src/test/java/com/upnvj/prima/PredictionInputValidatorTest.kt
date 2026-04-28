package com.upnvj.prima

import com.fik.upnvj.ews.ui.main.PredictionInputKeys
import com.fik.upnvj.ews.ui.main.PredictionInputValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PredictionInputValidatorTest {

    @Test
    fun validInputBuildsExpectedPredictRequest() {
        val result = PredictionInputValidator.validate(
            ips1 = "3.42",
            ips2 = "3.31",
            ips3 = "3.18",
            ips4 = "3.08",
            sks = "84",
            gender = 1
        )

        assertTrue(result.isValid)
        assertNotNull(result.request)
        assertEquals(3.42, result.request!!.ips1, 0.0)
        assertEquals(3.31, result.request.ips2, 0.0)
        assertEquals(3.18, result.request.ips3, 0.0)
        assertEquals(3.08, result.request.ips4, 0.0)
        assertEquals(84, result.request.sks)
        assertEquals(1, result.request.jk)
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun ipsOutsideRangeReturnsValidationError() {
        val result = PredictionInputValidator.validate(
            ips1 = "4.10",
            ips2 = "3.31",
            ips3 = "3.18",
            ips4 = "3.08",
            sks = "84",
            gender = 1
        )

        assertFalse(result.isValid)
        assertTrue(result.errors.containsKey(PredictionInputKeys.IPS1))
    }

    @Test
    fun emptyOrInvalidSksReturnsValidationError() {
        val emptyResult = PredictionInputValidator.validate(
            ips1 = "3.42",
            ips2 = "3.31",
            ips3 = "3.18",
            ips4 = "3.08",
            sks = "",
            gender = 1
        )
        val outOfRangeResult = PredictionInputValidator.validate(
            ips1 = "3.42",
            ips2 = "3.31",
            ips3 = "3.18",
            ips4 = "3.08",
            sks = "101",
            gender = 1
        )

        assertTrue(emptyResult.errors.containsKey(PredictionInputKeys.SKS))
        assertTrue(outOfRangeResult.errors.containsKey(PredictionInputKeys.SKS))
    }

    @Test
    fun missingGenderReturnsValidationError() {
        val result = PredictionInputValidator.validate(
            ips1 = "3.42",
            ips2 = "3.31",
            ips3 = "3.18",
            ips4 = "3.08",
            sks = "84",
            gender = null
        )

        assertFalse(result.isValid)
        assertTrue(result.errors.containsKey(PredictionInputKeys.GENDER))
    }
}
