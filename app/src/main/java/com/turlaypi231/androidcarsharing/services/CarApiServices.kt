package com.turlaypi231.androidcarsharing.services

import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.Trip
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarApiService {
    @GET("/api/v1/cars")
    suspend fun getAllCars(): List<Car>

    @POST("api/v1/users/{trip_id}/finish")
    suspend fun finishTrip(@Path("trip_id") tripId: Int): Response<Unit>

    @POST("api/v1/cars/{car_id}/book")
    suspend fun bookCar(@Path("car_id") carId: Int): Response<Trip>
}
