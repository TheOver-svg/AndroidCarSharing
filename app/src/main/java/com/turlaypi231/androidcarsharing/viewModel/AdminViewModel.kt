package com.turlaypi231.androidcarsharing.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.model.AdminTripResponse
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.CarCreate

class AdminViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<AdminTripResponse>>(emptyList())
    val trips: StateFlow<List<AdminTripResponse>> = _trips.asStateFlow()

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> = _cars.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun fetchAllTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.carApi.getAllTripsAdmin()
                if (response.isSuccessful) {
                    _trips.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("AdminVM", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAllCars() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.carApi.getAllCars()
                _cars.value = response
            } catch (e: Exception) {
                Log.e("AdminVM", "Error cars: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCar(carId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.carApi.deleteCar(carId)
                if (response.isSuccessful) {
                    fetchAllCars()
                } else {
                    _errorMessage.value = "Не вдалося видалити: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("AdminVM", "Delete error: ${e.message}")
            }
        }
    }

    fun addCar(car: CarCreate, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.carApi.createCar(car)
                if (response.isSuccessful) {
                    onSuccess()
                    fetchAllCars()
                } else {
                    _errorMessage.value = "Помилка додавання: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("AdminVM", "Add error: ${e.message}")
            }
        }
    }

    fun clearError() { _errorMessage.value = null }
}