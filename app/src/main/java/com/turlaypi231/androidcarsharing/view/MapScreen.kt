package com.turlaypi231.androidcarsharing.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.turlaypi231.androidcarsharing.viewModel.MapViewModel
import com.turlaypi231.androidcarsharing.R
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.utills.bitmapDescriptorFromVector
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MapViewModel = viewModel(), navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val peekHeight by animateDpAsState(
        targetValue = if (uiState.selectedCar != null) 280.dp else 0.dp,
        label = "peekHeight"
    )

    LaunchedEffect(uiState.selectedCar) {
        if (uiState.selectedCar != null) {
            bottomSheetState.partialExpand()
        } else {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == SheetValue.Hidden && uiState.selectedCar != null) {
            viewModel.onDismissBottomSheet()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                Spacer(Modifier.height(36.dp))
                Text(
                    "Carsharing",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
                HorizontalDivider()
                Spacer(Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = { Text("Профіль") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("profile")
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Історія поїздок") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = peekHeight,
            sheetDragHandle = { BottomSheetDefaults.DragHandle() },
            sheetContent = {
                if (uiState.selectedCar != null) {
                    CarDetailsSheet(uiState.selectedCar!!,
                        onBookingSuccess = {})
                } else {
                    Box(modifier = Modifier.height(1.dp))
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {

                MapContent(
                    modifier = Modifier.fillMaxSize(),
                    cars = uiState.cars,
                    onCarClick = { car -> viewModel.onClickCar(car) },
                    onMapClick = {
                        viewModel.onDismissBottomSheet()
                        scope.launch { drawerState.close() }
                    }
                )

                FloatingActionButton(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 48.dp, start = 16.dp),
                    containerColor = Color(0xFFFF9800),
                    contentColor = Color.Black,
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu_24px),
                        contentDescription = "Меню",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    cars: List<Car>,
    onCarClick: (Car) -> Unit,
    onMapClick: () -> Unit
) {
    val khmelnytskyi = LatLng(49.42, 26.98)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(khmelnytskyi, 12f)
    }

    val context = LocalContext.current
    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
            isMyLocationEnabled = false,
            maxZoomPreference = 20f,
            minZoomPreference = 5f
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            mapToolbarEnabled = false,
            compassEnabled = false
        ),
        onMapClick = { onMapClick() }
    ) {
        val carIcon = bitmapDescriptorFromVector(context, R.drawable.ic_car)
        cars.forEach { car ->
            Marker(
                state = MarkerState(position = car.location.toLatLng()),
                title = car.model,
                icon = carIcon,
                onClick = {
                    onCarClick(car)
                    true
                }
            )
        }
    }
}