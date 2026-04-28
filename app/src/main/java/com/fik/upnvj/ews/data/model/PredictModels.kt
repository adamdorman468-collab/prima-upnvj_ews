package com.fik.upnvj.ews.data.model

import com.google.gson.annotations.SerializedName

data class PredictRequest(
    @SerializedName("ips1") val ips1: Double,
    @SerializedName("ips2") val ips2: Double,
    @SerializedName("ips3") val ips3: Double,
    @SerializedName("ips4") val ips4: Double,
    @SerializedName("sks") val sks: Int,
    @SerializedName("jk") val jk: Int
)

data class PredictResponse(
    @SerializedName("status") val status: String,
    @SerializedName("prediction") val prediction: Int,
    @SerializedName("confidence") val confidence: Double,
    @SerializedName("message") val message: String
)
