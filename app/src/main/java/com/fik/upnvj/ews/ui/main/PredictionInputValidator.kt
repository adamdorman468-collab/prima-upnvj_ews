package com.fik.upnvj.ews.ui.main

import com.fik.upnvj.ews.data.model.PredictRequest

data class PredictionValidationResult(
    val request: PredictRequest?,
    val errors: Map<String, String>
) {
    val isValid: Boolean = request != null && errors.isEmpty()
}

object PredictionInputKeys {
    const val IPS1 = "ips1"
    const val IPS2 = "ips2"
    const val IPS3 = "ips3"
    const val IPS4 = "ips4"
    const val SKS = "sks"
    const val GENDER = "gender"
}

object PredictionInputValidator {
    fun validate(
        ips1: String,
        ips2: String,
        ips3: String,
        ips4: String,
        sks: String,
        gender: Int?
    ): PredictionValidationResult {
        val errors = mutableMapOf<String, String>()

        val parsedIps1 = parseIps(PredictionInputKeys.IPS1, ips1, errors)
        val parsedIps2 = parseIps(PredictionInputKeys.IPS2, ips2, errors)
        val parsedIps3 = parseIps(PredictionInputKeys.IPS3, ips3, errors)
        val parsedIps4 = parseIps(PredictionInputKeys.IPS4, ips4, errors)
        val parsedSks = parseSks(sks, errors)

        if (gender == null) {
            errors[PredictionInputKeys.GENDER] = "Pilih jenis kelamin."
        } else if (gender !in 0..1) {
            errors[PredictionInputKeys.GENDER] = "Jenis kelamin tidak valid."
        }

        val request = if (errors.isEmpty()) {
            PredictRequest(
                ips1 = parsedIps1!!,
                ips2 = parsedIps2!!,
                ips3 = parsedIps3!!,
                ips4 = parsedIps4!!,
                sks = parsedSks!!,
                jk = gender!!
            )
        } else {
            null
        }

        return PredictionValidationResult(request = request, errors = errors)
    }

    private fun parseIps(
        key: String,
        value: String,
        errors: MutableMap<String, String>
    ): Double? {
        val parsed = value.trim().replace(',', '.').toDoubleOrNull()
        if (parsed == null || parsed !in 0.0..4.0) {
            errors[key] = "IPS harus angka 0.00 sampai 4.00."
            return null
        }
        return parsed
    }

    private fun parseSks(
        value: String,
        errors: MutableMap<String, String>
    ): Int? {
        val parsed = value.trim().toIntOrNull()
        if (parsed == null || parsed !in 0..88) {
            errors[PredictionInputKeys.SKS] = "Total SKS harus angka 0 sampai 100."
            return null
        }
        return parsed
    }
}
