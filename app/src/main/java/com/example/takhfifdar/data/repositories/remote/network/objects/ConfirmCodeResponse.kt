package com.example.takhfifdar.data.repositories.remote.network.objects

import com.example.takhfifdar.data.repositories.local.database.User
import com.google.gson.annotations.SerializedName

data class ConfirmCodeResponse(
    val user: User,
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)
