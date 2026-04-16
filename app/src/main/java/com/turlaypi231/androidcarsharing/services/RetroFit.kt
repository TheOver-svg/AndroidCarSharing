package com.turlaypi231.androidcarsharing.services
import com.google.gson.GsonBuilder
import com.turlaypi231.androidcarsharing.model.Car
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/"
    private val gson = GsonBuilder()
        .registerTypeAdapter(Car::class.java, CarDeserializer())
        .create()

    val api: CarApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CarApiService::class.java)
    }
}