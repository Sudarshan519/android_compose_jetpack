package com.example.retrofitexample

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getPosts(): ApiBase
}
data class ApiBase(

    val  data:List<Post>
)


data class Post(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name:String,
    val avatar:String

)