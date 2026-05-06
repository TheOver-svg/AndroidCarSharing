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

class AdminViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<AdminTripResponse>>(emptyList())
    val trips: StateFlow<List<AdminTripResponse>> = _trips.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchAllTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.carApi.getAllTripsAdmin()
                if (response.isSuccessful) {
                    _trips.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("AdminVM", "Error fetching trips: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}