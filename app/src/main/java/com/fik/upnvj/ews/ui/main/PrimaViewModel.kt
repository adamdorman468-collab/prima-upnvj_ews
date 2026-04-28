package com.fik.upnvj.ews.ui.main

import androidx.lifecycle.ViewModel
import com.fik.upnvj.ews.data.model.DashboardSummary
import com.fik.upnvj.ews.data.model.ProfileInfo
import com.fik.upnvj.ews.data.model.ProgressEntry
import com.fik.upnvj.ews.data.model.WarningSummary
import com.fik.upnvj.ews.data.repository.DummyDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PrimaUiState(
    val profile: ProfileInfo,
    val dashboard: DashboardSummary,
    val warning: WarningSummary,
    val progress: List<ProgressEntry>,
    val isLoggedIn: Boolean = false
)

class PrimaViewModel(
    private val repository: DummyDataRepository = DummyDataRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PrimaUiState(
            profile = repository.getProfile(),
            dashboard = repository.getDashboardSummary(),
            warning = repository.getWarningSummary(),
            progress = repository.getProgressHistory()
        )
    )
    val uiState: StateFlow<PrimaUiState> = _uiState.asStateFlow()

    fun login(nim: String, password: String) {
        _uiState.update { state ->
            state.copy(isLoggedIn = true)
        }
    }

    fun logout() {
        _uiState.update { state -> state.copy(isLoggedIn = false) }
    }
}
