package com.example.retrofitexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"

    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    fun handleApiError(responseCode: Int) {
        // Handle specific HTTP response codes here
        when (responseCode) {
            400 -> {
                // Bad Request
                // Handle error case
            }
            401 -> {
                // Unauthorized
                // Handle error case
            }
            403 -> {
                // Forbidden
                // Handle error case
            }
            404 -> {
                // Not Found
                // Handle error case
            }
            // and so on...
            else -> {
                // Generic error handling
                // Handle other response codes
            }
        }
    }
}