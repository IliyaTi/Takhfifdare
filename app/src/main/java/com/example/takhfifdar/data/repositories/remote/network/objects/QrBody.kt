package com.example.takhfifdar.data.repositories.remote.network.objects

import com.google.gson.annotations.SerializedName

data class QrBody(
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("user_id")
    val userId: Int
)
