package com.turlaypi231.androidcarsharing.services
import com.google.gson.GsonBuilder
import com.turlaypi231.androidcarsharing.data.remote.AuthApi
import com.turlaypi231.androidcarsharing.model.Car
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TokenManager {
    var token: String? = null
}
object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.101:8000/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            TokenManager.token?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            chain.proceed(requestBuilder.build())
        }
        .build()
    private val gson = GsonBuilder()
        .registerTypeAdapter(Car::class.java, CarDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val carApi: CarApiService by lazy {
        retrofit.create(CarApiService::class.java)
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}