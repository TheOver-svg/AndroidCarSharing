package com.turlaypi231.androidcarsharing.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turlaypi231.androidcarsharing.model.AdminTripResponse
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.CarCreate
import com.turlaypi231.androidcarsharing.model.LocationDto
import com.turlaypi231.androidcarsharing.ui.theme.DarkBackground
import com.turlaypi231.androidcarsharing.ui.theme.DarkSurface
import com.turlaypi231.androidcarsharing.ui.theme.OrangePrimary
import com.turlaypi231.androidcarsharing.viewModel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
    onBackClick: () -> Unit
) {
    val trips by viewModel.trips.collectAsState()
    val cars by viewModel.cars.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddCarDialog by remember { mutableStateOf(false) }
    val totalRevenue = trips.filter { it.status == "finished" }
        .sumOf { it.totalCost ?: 0.0 }
    val finishedTripsCount = trips.count { it.status == "finished" }

    LaunchedEffect(selectedTab) {
        if (selectedTab == 0 || selectedTab == 2) viewModel.fetchAllTrips()
        if (selectedTab == 1) viewModel.fetchAllCars()
    }

    if (showAddCarDialog) {
        AddCarDialog(
            onDismiss = { showAddCarDialog = false },
            onConfirm = { newCar ->
                viewModel.addCar(newCar) { showAddCarDialog = false }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Адмін-панель", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        floatingActionButton = {
            if (selectedTab == 1) {
                FloatingActionButton(
                    onClick = { showAddCarDialog = true },
                    containerColor = OrangePrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Додати машину", tint = Color.Black)
                }
            }
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = DarkSurface,
                contentColor = OrangePrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = OrangePrimary
                    )
                }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Бронювання") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Автопарк") })
                Tab(selected = selectedTab == 2, onClick = {selectedTab = 2}, text = {Text("Дохід")})
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = OrangePrimary)
            }
            when (selectedTab) {
                0 -> { // Вкладка Бронювання
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(trips) { trip -> AdminTripCard(trip) }
                    }
                }
                1 -> { // Вкладка Автопарк
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(cars) { car ->
                            AdminCarCard(car = car, onDelete = { viewModel.deleteCar(it) })
                        }
                    }
                }
                2 -> { // Вкладка Дохід
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = DarkSurface),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Загальний дохід", color = Color.Gray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "${String.format("%.2f", totalRevenue)} ₴",
                                    color = Color.White,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatSmallCard(
                                label = "Завершено",
                                value = "$finishedTripsCount",
                                modifier = Modifier.weight(1f)
                            )
                            val avg = if (finishedTripsCount > 0) totalRevenue / finishedTripsCount else 0.0
                            StatSmallCard(
                                label = "Середній чек",
                                value = "${String.format("%.1f", avg)} ₴",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminTripCard(trip: AdminTripResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = trip.carModel,
                    style = MaterialTheme.typography.titleMedium,
                    color = OrangePrimary,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (trip.status == "active") Color(0xFF2E7D32) else Color.Gray
                ) {
                    Text(
                        text = trip.status.uppercase(),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Клієнт: ${trip.userName}", color = Color.White)
            Text(text = "Email: ${trip.userEmail}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray.copy(alpha = 0.2f))
            Text(
                text = "ID поїздки: #${trip.tripId}",
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = if (trip.totalCost != null) "${trip.totalCost} ₴" else "Рахується...",
                color = if (trip.totalCost != null) OrangePrimary else Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminCarCard(car: Car, onDelete: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = car.model, color = OrangePrimary, fontWeight = FontWeight.Bold)
                Text(text = "Номер: ${car.plateNumber}", color = Color.White)
                Text(text = "Тип: ${car.engineType}", color = Color.Gray)
            }
            IconButton(onClick = {
                val idInt = car.id.toIntOrNull() ?: 0
                onDelete(idInt)
            }) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
            }
        }
    }
}

@Composable
fun AddCarDialog(onDismiss: () -> Unit, onConfirm: (CarCreate) -> Unit) {
    var inputModel by remember { mutableStateOf("") }
    var inputPlate by remember { mutableStateOf("") }
    var inputPrice by remember { mutableStateOf("") }
    var inputEngineType by remember { mutableStateOf("gasoline") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = { Text("Нове авто", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = inputModel,
                    onValueChange = { inputModel = it },
                    label = { Text("Модель") }
                )
                OutlinedTextField(
                    value = inputPlate,
                    onValueChange = { inputPlate = it },
                    label = { Text("Держ. номер") }
                )
                OutlinedTextField(
                    value = inputPrice,
                    onValueChange = { inputPrice = it },
                    label = { Text("Ціна (хв)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = inputEngineType == "gasoline",
                        onClick = { inputEngineType = "gasoline" }
                    )
                    Text("Бензин", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = inputEngineType == "electric",
                        onClick = { inputEngineType = "electric" }
                    )
                    Text("Електро", color = Color.White)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val newCar = CarCreate(
                    model = inputModel,
                    transmission = "Automatic",
                    price = inputPrice.toDoubleOrNull()?.toInt() ?: 0,
                    engineType = inputEngineType,
                    plateNumber = inputPlate,
                    description = "Added by admin", // hard code
                    location = LocationDto(49.4229, 26.9871), // hard code locate
                    fuelLevel = if (inputEngineType == "gasoline") 100 else null,
                    batteryLevel = if (inputEngineType == "electric") 100 else null
                )
                onConfirm(newCar)
            }) { Text("ДОДАТИ", color = OrangePrimary) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("СКАСУВАТИ", color = Color.Gray) }
        }
    )
}

@Composable
fun StatSmallCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, color = Color.Gray, style = MaterialTheme.typography.labelMedium)
            Text(value, color = OrangePrimary, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}