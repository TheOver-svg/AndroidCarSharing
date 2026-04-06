package com.turlaypi231.androidcarsharing.model

import com.google.android.gms.maps.model.LatLng


data class Car(
    val id: String,
    val model: String,
    val price: Int,
    val location: LatLng,
    val fuelLevel: Int,
    val plateNumber: String
)