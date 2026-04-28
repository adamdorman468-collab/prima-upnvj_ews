package com.upnvj.prima

import com.fik.upnvj.ews.ui.main.SignupInputKeys
import com.fik.upnvj.ews.ui.main.SignupInputValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SignupInputValidatorTest {

    @Test
    fun validSignupCreatesExpectedUserProfile() {
        val result = SignupInputValidator.validate(
            name = "Dina",
            nim = "2410512001",
            faculty = "Fakultas Ilmu Komputer",
            studyProgram = "Sistem Informasi",
            cohort = "2024",
            password = "secret",
            confirmPassword = "secret"
        )

        assertTrue(result.isValid)
        assertNotNull(result.profile)
        assertEquals("Dina", result.profile!!.name)
        assertEquals("2410512001", result.profile.nim)
        assertEquals("Sistem Informasi", result.profile.studyProgram)
        assertEquals("2024", result.profile.cohort)
        assertEquals("secret", result.profile.password)
    }

    @Test
    fun missingRequiredFieldsReturnsValidationErrors() {
        val result = SignupInputValidator.validate(
            name = "",
            nim = "",
            faculty = "Fakultas Ilmu Komputer",
            studyProgram = "",
            cohort = "",
            password = "secret",
            confirmPassword = "secret"
        )

        assertFalse(result.isValid)
        assertTrue(result.errors.containsKey(SignupInputKeys.NAME))
        assertTrue(result.errors.containsKey(SignupInputKeys.NIM))
        assertTrue(result.errors.containsKey(SignupInputKeys.STUDY_PROGRAM))
        assertTrue(result.errors.containsKey(SignupInputKeys.COHORT))
    }

    @Test
    fun passwordMismatchReturnsValidationError() {
        val result = SignupInputValidator.validate(
            name = "Dina",
            nim = "2410512001",
            faculty = "Fakultas Ilmu Komputer",
            studyProgram = "Sistem Informasi",
            cohort = "2024",
            password = "secret",
            confirmPassword = "different"
        )

        assertFalse(result.isValid)
        assertTrue(result.errors.containsKey(SignupInputKeys.CONFIRM_PASSWORD))
    }
}
