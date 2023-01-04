package com.example.takhfifdar.data.repositories.remote.network.objects

import com.google.gson.annotations.SerializedName

data class EditProfileBody(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val name: String,
    val email: String,
    val phone: String,
    val city: String,
    @SerializedName("birth_date")
    val birthDate: String
)
