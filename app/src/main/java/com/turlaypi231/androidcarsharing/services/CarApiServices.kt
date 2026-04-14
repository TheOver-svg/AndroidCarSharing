package com.turlaypi231.androidcarsharing.services

import com.turlaypi231.androidcarsharing.model.Car
import retrofit2.http.GET

interface CarApiService {
    @GET("/api/v1/cars")
    suspend fun getAllCars(): List<Car>
}
