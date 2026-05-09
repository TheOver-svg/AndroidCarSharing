package com.turlaypi231.androidcarsharing.data

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String
)

data class AuthResponse(
    val token: String?,
    val message: String
)