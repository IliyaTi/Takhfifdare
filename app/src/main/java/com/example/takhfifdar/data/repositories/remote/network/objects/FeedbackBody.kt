package com.example.takhfifdar.data.repositories.remote.network.objects

import com.google.gson.annotations.SerializedName


data class FeedbackBody(
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("user_id")
    val userId: Int,
//    val positive: Int,    CHANGED SINCE 1.1.2
//    val negative: Int,    CHANGED SINCE 1.1.2
    val score: Float,      // ADDED SINCE 1.1.2
    val reaction: Int,
    val comment: String
)
