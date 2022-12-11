package com.example.takhfifdar.data.network.objects

import com.google.gson.annotations.SerializedName


data class FeedbackBody(
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("user_id")
    val userId: Int,
    val positive: Int,
    val negative: Int,
    val reaction: Int
)
