package com.example.assignment11

import com.example.assignment11.model.AuthUserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    // 세종대학교 로그인 API
    @POST("/auth?method=ClassicSession")
    fun login(@Body body: Map<String, String>): Call<SejongAuthResponse>

    @POST("/users/login") // 동일한 엔드포인트로 사용자 정보 전송
    fun sendUserInfoToServer(@Body userDTO: AuthUserDTO): Call<ServerResponse>

    @POST("/users/login") // 동일한 엔드포인트로 JWT 토큰 요청
    fun requestJwtToken(@Body userDTO: AuthUserDTO): Call<JwtResponse>
}
