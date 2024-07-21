package com.example.assignment11

object JwtProvider {
    private var token: String? = null

    fun setToken(token: String?) {
        this.token = token
    }

    fun getToken(): String? {
        return token
    }
}
