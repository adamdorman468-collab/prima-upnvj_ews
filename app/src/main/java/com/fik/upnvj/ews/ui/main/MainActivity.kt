package com.fik.upnvj.ews.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fik.upnvj.ews.ui.navigation.AppNavigation
import com.fik.upnvj.ews.ui.theme.PrimaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimaTheme {
                AppNavigation()
            }
        }
    }
}
