package com.turlaypi231.androidcarsharing.data.remote

import com.turlaypi231.androidcarsharing.data.TripResponse
import com.turlaypi231.androidcarsharing.data.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT


interface UserApi {
    @GET("api/v1/users/me")
    suspend fun getMyProfile(): Response<UserProfile>

    @GET("api/v1/users/me/trips")
    suspend fun getMyTrips(): Response<List<TripResponse>>

    @PUT("api/v1/users/me")
    suspend fun updateProfile(@Body data: Map<String, String>): Response<Map<String, String>>
}