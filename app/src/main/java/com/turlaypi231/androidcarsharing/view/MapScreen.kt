package com.turlaypi231.androidcarsharing.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.viewModel.MapViewModel
import com.turlaypi231.androidcarsharing.R
import com.turlaypi231.androidcarsharing.utills.bitmapDescriptorFromVector

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
                CarDetailsSheet(car)
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
                ),
                onMapClick = {
                    viewModel.onDismissBottomSheet()
                }
            ) {
                val context = LocalContext.current
                val carIcon = bitmapDescriptorFromVector(context, R.drawable.ic_car)
                uiState.cars.forEach { car ->
                    Marker(
                        state = MarkerState(position = car.location),
                        title = car.model,
                        icon = carIcon,
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