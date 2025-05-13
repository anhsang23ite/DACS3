package com.example.app_tblxa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.app_tblxa1.navigation.AppNavigation
import com.example.app_tblxa1.ui.theme.APP_TBLXA1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP_TBLXA1Theme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF3F4F6)
    ) {
        AppNavigation() // Navigation function
    }
}