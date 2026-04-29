package com.turlaypi231.androidcarsharing.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import com.turlaypi231.androidcarsharing.view.MapUiState
import kotlinx.coroutines.delay
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
    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                loadCars()
                delay(15000)
            }
        }
    }

    fun loadCars() {
        viewModelScope.launch {
            try {

                val fetchedCars = RetrofitClient.carApi.getAllCars()

                _uiState.update { currentState ->
                    currentState.copy(cars = fetchedCars)
                }
                Log.d("MapViewModel", "Завантажено авто: ${fetchedCars.size}")
            } catch (e: Exception) {
                Log.e("MapViewModel", "Помилка API: ${e.message}")
            }
        }
    }

    fun reserveCar(carId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.carApi.bookCar(carId)
                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("BOOKING", "Помилка бронювання: ${e.message}")
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