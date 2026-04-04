package com.turlaypi231.androidcarsharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.turlaypi231.androidcarsharing.ui.theme.AndroidCarSharingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCarSharingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MyMapScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MyMapScreen() {
    val khmelnytskyi = LatLng(49.42, 26.98)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(khmelnytskyi, 12f)
    }

    val mapUiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    AndroidCarSharingTheme {
        MyMapScreen()
    }
}