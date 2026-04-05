package com.turlaypi231.androidcarsharing.view

import com.turlaypi231.androidcarsharing.model.Car

data class MapUiState (
    val cars: List<Car> = emptyList(),
    val selectedCar: Car? = null,
    val isLoading: Boolean = false
)