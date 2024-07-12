package com.example.assignment11

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class Credentials(
    val id: String,
    val pw: String
)

interface Api {
    @POST("/auth")
    fun authenticate(@Body credentials: Credentials): Call<SejongAuthResponse>
}