package com.example.assignment11

data class SejongAuthResponse(
    val msg:String,
    val result:SejongAuthResponseResultJson
)

data class SejongAuthResponseResultJson(
    val authenticator:String,
    val code:String,
    val is_auth:String,
    val status_code:String,
    val success:String,
    val body:SejongAuthResponseResultBodyJson
)

data class SejongAuthResponseResultBodyJson(
    val name:String,
    val major:String
)