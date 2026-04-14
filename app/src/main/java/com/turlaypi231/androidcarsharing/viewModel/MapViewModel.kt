package com.turlaypi231.androidcarsharing.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import com.turlaypi231.androidcarsharing.view.MapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCars()
    }

    private fun loadCars() {
        viewModelScope.launch {
            try {
                val fetchedCars = RetrofitClient.api.getAllCars()
                _uiState.update { currentState ->
                    currentState.copy(cars = fetchedCars)
                }
                Log.d("MapViewModel", "Завантажено авто: ${fetchedCars.size}")
            } catch (e: Exception) {
                Log.e("MapViewModel", "Помилка API: ${e.message}")
            }
        }
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