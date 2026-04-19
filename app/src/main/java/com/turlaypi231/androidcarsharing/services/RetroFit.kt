package com.turlaypi231.androidcarsharing.services
import com.google.gson.GsonBuilder
import com.turlaypi231.androidcarsharing.data.remote.AuthApi
import com.turlaypi231.androidcarsharing.model.Car
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.105:8000/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(Car::class.java, CarDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val carApi: CarApiService by lazy {
        retrofit.create(CarApiService::class.java)
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}