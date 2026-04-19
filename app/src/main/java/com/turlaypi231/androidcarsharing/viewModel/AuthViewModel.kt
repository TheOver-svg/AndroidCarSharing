package com.turlaypi231.androidcarsharing.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turlaypi231.androidcarsharing.model.User
import com.turlaypi231.androidcarsharing.services.RetrofitClient
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    fun register(name: String, email: String, phone: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val newUser = User(fullName = name, email = email, phone = phone, password = pass)
                val response = RetrofitClient.authApi.register(newUser)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    android.util.Log.e("API_ERROR", "Помилка: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("API_ERROR", "Мережа: ${e.message}")
            }
        }
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val credentials = mapOf("email" to email, "password" to pass)
                val response = RetrofitClient.authApi.login(credentials)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    android.util.Log.e("API_ERROR", "Помилка входу")
                }
            } catch (e: Exception) {
                android.util.Log.e("API_ERROR", "Мережа: ${e.message}")
            }
        }
    }
}