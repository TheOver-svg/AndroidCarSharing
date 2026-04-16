package com.turlaypi231.androidcarsharing.services

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.turlaypi231.androidcarsharing.model.Car
import com.turlaypi231.androidcarsharing.model.ElectricCar
import com.turlaypi231.androidcarsharing.model.GasolineCar
import java.lang.reflect.Type

class CarDeserializer : JsonDeserializer<Car> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Car {
        val jsonObject = json.asJsonObject
        val engineType = jsonObject.get("engine_type")?.asString

        return when (engineType) {
            "electric" -> context.deserialize(json, ElectricCar::class.java)
            "gasoline" -> context.deserialize(json, GasolineCar::class.java)
            else -> throw IllegalArgumentException("Невідомий тип двигуна: $engineType")
        }
    }
}