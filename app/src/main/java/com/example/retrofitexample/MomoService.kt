package com.example.retrofitexample

import retrofit2.http.GET
import retrofit2.http.Headers

interface MomoService {
    @Headers("Accept: application/json")
    @GET("audiobook/home?language=nepali")
    suspend fun getHome(): BaseResponse

}



data class BaseResponse (
    val data: PageResponse? = null
)

data class PageResponse (
    val name: String? = null,
    val slug: String? = null,
    val items: List<AudioBookList>? = null,
    val totalItems: Long? = null,
    val perPageItems: Long? = null,
    val prevPageURL: String? = null,
    val nextPageURL: String? = null,
    val totalPage: Long? = null
)

data class AudioBookList (
    val name: String? = null,
    val slug: String? = null,
    val items: List<AudioBook>? = null,
    val totalItems: Long? = null,
    val perPageItems: Long? = null,
    val prevPageURL: String? = null,
    val nextPageURL: String? = null,
    val totalPage: Long? = null
)

data class AudioBook (
    val type: Type? = null,
    val title: String? = null,
    val slug: String? = null,
    val logo: String? = null,
    val name: String? = null,
    val redirectLink: String? = null,
    val description: String? = null,
    val language: String? = null,
    val background: String? = null,
    val isPurchase: String? = null,
    val purchaseType: String? = null,
    val prices: List<Any?>? = null,
    val genre: String? = null,
    val contentProvider: ContentProvider? = null
)

data class ContentProvider (
    val id: Long? = null,
    val name: String? = null,
    val logo: String? = null
)

enum class Type {
    Audiobook,
    Genre
}
