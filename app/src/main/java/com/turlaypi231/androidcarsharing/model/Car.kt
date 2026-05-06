package com.turlaypi231.androidcarsharing.model

import com.google.gson.annotations.SerializedName

data class LocationDto(val latitude: Double, val longitude: Double) {
    fun toLatLng() = com.google.android.gms.maps.model.LatLng(latitude, longitude)
}
sealed class Car {
    abstract val id: String
    abstract val model: String
    abstract val transmission: String
    abstract val price: Int
    abstract val location: LocationDto
    abstract val description: String
    @get:SerializedName("plate_number") abstract val plateNumber: String
    @get:SerializedName("engine_type") abstract val engineType: String
}

data class Trip(
    @SerializedName("id") val id: Int,
    @SerializedName("car_id") val carId: Int,
    @SerializedName("status") val status: String,
    @SerializedName("car_model") val carModel: String
)