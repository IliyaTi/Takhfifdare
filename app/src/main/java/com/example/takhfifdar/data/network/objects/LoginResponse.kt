package com.example.takhfifdar.data.network.objects

import com.example.takhfifdar.data.database.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val user: User,
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)
