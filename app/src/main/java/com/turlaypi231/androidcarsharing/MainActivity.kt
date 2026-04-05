package com.turlaypi231.androidcarsharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.turlaypi231.androidcarsharing.ui.theme.AndroidCarSharingTheme
import com.turlaypi231.androidcarsharing.view.MapScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCarSharingTheme {
                MapScreen()
            }
        }
    }
}