package com.turlaypi231.androidcarsharing.model

import com.google.gson.annotations.SerializedName

data class AdminTripResponse(
    @SerializedName("trip_id") val tripId: Int,
    @SerializedName("user_name") val userName: String,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("car_model") val carModel: String,
    @SerializedName("status") val status: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("total_cost") val totalCost: Double?
)