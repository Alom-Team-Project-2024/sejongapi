package com.example.assignment11

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("/auth?method=MoodlerSession")
    fun login(
        @Body body: Map<String, String>
    ): Call<SejongAuthResponse>
}