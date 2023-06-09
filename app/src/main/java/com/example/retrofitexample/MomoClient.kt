package com.example.retrofitexample

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MomoClient {
    private const val BASE_URL = "https://mnmdev1.truestreamz.com/api/v1/"

    val apiService: MomoService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
//            .client(OkHttpClient.Builder().addInterceptor {
////                    chain ->
////                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjksImlzcyI6Imh0dHBzOi8vbW5tZGV2MS50cnVlc3RyZWFtei5jb20vYXBpL25ldC10di9sb2dpbiIsImlhdCI6MTY3MzM0MjI0MCwiZXhwIjoxNzQ0MTM0MjI0MCwibmJmIjoxNjczMzQyMjQwLCJqdGkiOiJzT2dVQkU0bmtwRWtuVmVWIn0.tn6EUEV6bi8Vlx89EeZ-jtaHMcTiA7czZ6AeUH0cjF0").build()
////                chain.proceed(request)
//            }.build())
            .build()

        retrofit.create(MomoService::class.java)
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