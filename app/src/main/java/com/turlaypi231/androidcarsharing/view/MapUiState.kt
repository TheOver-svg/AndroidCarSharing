package com.turlaypi231.androidcarsharing.view

import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.Trip

data class MapUiState (
    val cars: List<Car> = emptyList(),
    val selectedCar: Car? = null,
    val activeTrip: Trip? = null,
    val isLoading: Boolean = false
)