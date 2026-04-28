package com.fik.upnvj.ews.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fik.upnvj.ews.data.model.PredictRequest
import com.fik.upnvj.ews.data.model.PredictResponse
import com.fik.upnvj.ews.data.repository.MainRepository
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: PredictResponse) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Idle)
    val uiState: LiveData<UiState> = _uiState

    fun predict(
        ips1: Double,
        ips2: Double,
        ips3: Double,
        ips4: Double,
        sks: Int,
        jk: Int
    ) {
        val request = PredictRequest(ips1, ips2, ips3, ips4, sks, jk)
        
        _uiState.value = UiState.Loading
        
        viewModelScope.launch {
            try {
                val response = repository.predict(request)
                if (response.isSuccessful && response.body() != null) {
                    _uiState.postValue(UiState.Success(response.body()!!))
                } else {
                    _uiState.postValue(UiState.Error("Gagal mendapatkan prediksi: ${response.message()}"))
                }
            } catch (e: Exception) {
                _uiState.postValue(UiState.Error("Terjadi kesalahan: ${e.localizedMessage}"))
            }
        }
    }
}
