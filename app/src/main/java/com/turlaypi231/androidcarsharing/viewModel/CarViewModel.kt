package com.turlaypi231.androidcarsharing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> = _cars

    init {
        fetchCars()
    }

    private fun fetchCars() {
        viewModelScope.launch {
            try {
                val carList = RetrofitClient.api.getAllCars()
                _cars.value = carList
                Log.d("CarViewModel", "Успішно завантажено ${carList.size} авто")
            } catch (e: Exception) {
                Log.e("CarViewModel", "Помилка завантаження: ${e.message}")
            }
        }
    }
}