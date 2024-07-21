package com.example.assignment11.model

import com.example.assignment11.RegistrationStatus

data class AuthUserDTO(
    val username: String,
    val name: String,
    val major: String,
    val studentGrade: Int,
    val registrationStatus: RegistrationStatus
)
