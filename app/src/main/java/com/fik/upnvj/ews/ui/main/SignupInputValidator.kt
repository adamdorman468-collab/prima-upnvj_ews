package com.fik.upnvj.ews.ui.main

import com.fik.upnvj.ews.data.model.UserProfile

data class SignupValidationResult(
    val profile: UserProfile?,
    val errors: Map<String, String>
) {
    val isValid: Boolean = profile != null && errors.isEmpty()
}

object SignupInputKeys {
    const val NAME = "name"
    const val NIM = "nim"
    const val FACULTY = "faculty"
    const val STUDY_PROGRAM = "studyProgram"
    const val COHORT = "cohort"
    const val PASSWORD = "password"
    const val CONFIRM_PASSWORD = "confirmPassword"
}

object SignupInputValidator {
    fun validate(
        name: String,
        nim: String,
        faculty: String,
        studyProgram: String,
        cohort: String,
        password: String,
        confirmPassword: String
    ): SignupValidationResult {
        val errors = mutableMapOf<String, String>()
        val cleanName = name.trim()
        val cleanNim = nim.trim()
        val cleanFaculty = faculty.trim()
        val cleanStudyProgram = studyProgram.trim()
        val cleanCohort = cohort.trim()

        if (cleanName.isBlank()) errors[SignupInputKeys.NAME] = "Nama wajib diisi."
        if (cleanNim.isBlank()) errors[SignupInputKeys.NIM] = "NIM wajib diisi."
        if (cleanFaculty.isBlank()) errors[SignupInputKeys.FACULTY] = "Fakultas wajib diisi."
        if (cleanStudyProgram.isBlank()) errors[SignupInputKeys.STUDY_PROGRAM] = "Program studi wajib diisi."
        if (cleanCohort.isBlank()) errors[SignupInputKeys.COHORT] = "Angkatan wajib diisi."
        if (password.isBlank()) errors[SignupInputKeys.PASSWORD] = "Password wajib diisi."
        if (confirmPassword.isBlank()) {
            errors[SignupInputKeys.CONFIRM_PASSWORD] = "Konfirmasi password wajib diisi."
        } else if (password != confirmPassword) {
            errors[SignupInputKeys.CONFIRM_PASSWORD] = "Konfirmasi password tidak sama."
        }

        val profile = if (errors.isEmpty()) {
            UserProfile(
                name = cleanName,
                nim = cleanNim,
                faculty = cleanFaculty,
                studyProgram = cleanStudyProgram,
                cohort = cleanCohort,
                password = password
            )
        } else {
            null
        }

        return SignupValidationResult(profile = profile, errors = errors)
    }
}
