package com.fik.upnvj.ews.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fik.upnvj.ews.data.repository.SharedPreferencesSessionRepository
import com.fik.upnvj.ews.ui.main.PrimaViewModelFactory
import com.fik.upnvj.ews.ui.navigation.AppNavigation
import com.fik.upnvj.ews.ui.theme.PrimaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sessionRepository = remember {
                SharedPreferencesSessionRepository(
                    getSharedPreferences(PRIMA_SESSION_PREFS, MODE_PRIVATE)
                )
            }
            val factory = remember { PrimaViewModelFactory(sessionRepository) }
            val viewModel: PrimaViewModel = viewModel(factory = factory)

            PrimaTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }

    private companion object {
        const val PRIMA_SESSION_PREFS = "prima_local_session"
    }
}
