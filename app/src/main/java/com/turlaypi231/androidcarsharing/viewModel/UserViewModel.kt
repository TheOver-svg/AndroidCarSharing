package com.turlaypi231.androidcarsharing.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.data.TripResponse
import com.turlaypi231.androidcarsharing.data.UserProfile
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    private val _trips = MutableStateFlow<List<TripResponse>>(emptyList())
    val trips: StateFlow<List<TripResponse>> = _trips.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.userApi.getMyProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()
                } else {
                    android.util.Log.e("API_ERROR", "Профіль помилка: ${response.code()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("API_ERROR", "Мережа (профіль): ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchTrips() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.userApi.getMyTrips()
                if (response.isSuccessful) {
                    _trips.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                android.util.Log.e("API_ERROR", "Поїздки помилка: ${e.message}")
            }
        }
    }
}