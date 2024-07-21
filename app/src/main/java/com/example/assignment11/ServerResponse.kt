package com.example.assignment11

data class ServerResponse(
    val success: Boolean,
    val message: String,
    val data: UserData? // Optional: 서버 응답에 포함된 데이터
)

data class UserData(
    val name: String,
    val major: String,
    val grade: Int,
    val status: String
)