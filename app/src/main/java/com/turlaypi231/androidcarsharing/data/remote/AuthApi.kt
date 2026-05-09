package com.turlaypi231.androidcarsharing.data.remote

import com.turlaypi231.androidcarsharing.data.AuthResponse
import com.turlaypi231.androidcarsharing.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/v1/auth/register")
    suspend fun register(@Body user: User): Response<Unit>

    @POST("api/v1/auth/login")
    suspend fun login(@Body credentials: Map<String, String>): Response<AuthResponse>
}