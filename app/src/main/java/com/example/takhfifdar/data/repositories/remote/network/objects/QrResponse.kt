package com.example.takhfifdar.data.repositories.remote.network.objects

import com.example.takhfifdar.data.repositories.local.database.User
import com.google.gson.annotations.SerializedName

data class QrResponse(
    val user: User,
    @SerializedName("store_image")
    val storeImage: String?
)
