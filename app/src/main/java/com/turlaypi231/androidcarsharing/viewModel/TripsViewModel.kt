package com.turlaypi231.androidcarsharing.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.data.TripResponse
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TripsViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<TripResponse>>(emptyList())
    val trips: StateFlow<List<TripResponse>> = _trips.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun fetchTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Переконайся, що викликаєш правильний Api Service
                val response = RetrofitClient.userApi.getMyTrips()
                if (response.isSuccessful) {
                    _trips.value = response.body() ?: emptyList()
                } else {
                    Log.e("TripsViewModel", "Помилка: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TripsViewModel", "Помилка мережі: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}