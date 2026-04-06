package com.turlaypi231.androidcarsharing.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.view.MapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMockCar()
    }

    private fun loadMockCar()
    {
        val mockCars = listOf(
            Car(id = "1", model = "Tesla Model 3", price = 100, location = LatLng(49.423, 26.985), fuelLevel = 85, plateNumber = "ВХ0001НХ"),
            Car(id = "2", model = "VW ID.4", price = 80, location = LatLng(49.427, 26.978), fuelLevel = 40, plateNumber = "ВХ0002НХ"),
            Car(id = "3", model = "Renault Zoe", price = 50, location = LatLng(49.418, 26.992), fuelLevel = 15, plateNumber = "ВХ0003НХ")
        )
        _uiState.update { it.copy(cars = mockCars) }
    }

    fun onClickCar(car: Car)
    {
        _uiState.update { it.copy(selectedCar = car) }
    }

    fun onDismissBottomSheet()
    {
        _uiState.update { it.copy(selectedCar = null) }
    }
}