package com.example.takhfifdar.data.repositories.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "transaction_history")
data class Transaction(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("numberFactor")
    val billSerial: String,
    @SerializedName("phone")
    val vendorPhone: String,
    @SerializedName("storename")
    val vendorName: String,
    val date: String,
    val customer_first: String,
    val customer_last: String,
    @SerializedName("percent")
    val discountPercent: String,
    val countPage: String = ""
)