package com.fik.upnvj.ews.data.repository

import com.fik.upnvj.ews.data.api.ApiService
import com.fik.upnvj.ews.data.model.PredictRequest
import com.fik.upnvj.ews.data.model.PredictResponse
import retrofit2.Response

class MainRepository(private val apiService: ApiService) {
    suspend fun predict(request: PredictRequest): Response<PredictResponse> {
        return apiService.predict(request)
    }
}
