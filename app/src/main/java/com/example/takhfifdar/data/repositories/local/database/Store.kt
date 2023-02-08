package com.example.takhfifdar.data.repositories.local.database

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Store(
    val id: Int,
    val storeName: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("subcategory_id")
    val subcategoryId: Int,
    val text: String,
    val storeType: String,
    val status: Int,
    val percent: String,
    val web: String?,
    val instagram: String?,
    val telegram: String?,
    val city: String,
    val place: String,
    val address: String,
    val phone: Int,
    val email: String?,
    val second_phone: Int,
    @SerializedName("seller_id")
    val sellerId: Int,
    val serial: String,
    val latitude: String?,
    val longitude: String?,
    val sh_time_start: Int?,
    val sh_time_end: Int?,
    val ye_time_start: Int?,
    val ye_time_end: Int?,
    val do_time_start: Int?,
    val do_time_end: Int?,
    val se_time_start: Int?,
    val se_time_end: Int?,
    val ch_time_start: Int?,
    val ch_time_end: Int?,
    val pa_time_start: Int?,
    val pa_time_end: Int?,
    val jo_time_start: Int?,
    val jo_time_end: Int?,
    val re_time_start: Int?,
    val re_time_end: Int?,
    val updated_at: Timestamp
)
