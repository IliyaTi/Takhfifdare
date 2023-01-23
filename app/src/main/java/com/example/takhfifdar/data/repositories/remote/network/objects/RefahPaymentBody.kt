package com.example.takhfifdar.data.repositories.remote.network.objects

data class RefahPaymentBody (
    val price: Int,
    val user_id: Int,
    val type: Int,
    val discount: String?
)