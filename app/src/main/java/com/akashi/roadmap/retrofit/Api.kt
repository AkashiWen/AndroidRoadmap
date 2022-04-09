package com.akashi.roadmap.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface AccountApi {
    @GET("profile")
    fun getProfile(): Call<String>

    @GET("phone")
    suspend fun getPhone(): Response<String>
}