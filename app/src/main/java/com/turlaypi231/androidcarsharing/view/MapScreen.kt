package com.turlaypi231.androidcarsharing.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.viewModel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(uiState.selectedCar) {
        if (uiState.selectedCar != null) {
            scaffoldState.bottomSheetState.expand()
        } else {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            uiState.selectedCar?.let { car ->
                CarDetailsContent(car)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val khmelnytskyi = LatLng(49.42, 26.98)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(khmelnytskyi, 12f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = false
                )
            ) {
                uiState.cars.forEach { car ->
                    Marker(
                        state = MarkerState(position = car.location),
                        title = car.model,
                        onClick = {
                            viewModel.onClickCar(car)
                            true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarDetailsContent(car: Car) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding()
    ) {
        Text(
            text = car.model,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Доступно пального: ${car.fuelLevel}%",
            style = MaterialTheme.typography.bodyLarge,
            color = if (car.fuelLevel < 20) Color.Red else Color.Unspecified
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* booking logic here */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Забронювати")
        }
    }
}