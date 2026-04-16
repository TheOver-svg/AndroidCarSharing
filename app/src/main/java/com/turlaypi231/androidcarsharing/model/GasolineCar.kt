package com.turlaypi231.androidcarsharing.model

import com.google.gson.annotations.SerializedName

data class GasolineCar(
    override val id: String,
    override val model: String,
    override val transmission: String,
    override val price: Int,
    override val location: LocationDto,
    override val description: String,
    @get:SerializedName("plate_number") override val plateNumber: String,
    @get:SerializedName("engine_type") override val engineType: String,

    @SerializedName("fuel_level") val fuelLevel: Int
) : Car()