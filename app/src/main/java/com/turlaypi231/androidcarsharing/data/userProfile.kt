package com.turlaypi231.androidcarsharing.data

data class UserProfile(
    val email: String,
    val full_name: String,
    val phone: String
)

data class TripResponce(
    val trip_id: Int,
    val car_model: String,
    val start_time: String,
    val status: String,
    val total_cost: Double?
)