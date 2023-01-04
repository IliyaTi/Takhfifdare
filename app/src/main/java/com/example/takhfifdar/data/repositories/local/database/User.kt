package com.example.takhfifdar.data.repositories.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    var first_name: String? = "",
    var last_name: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var credit: String? = "",
    var birth_date: String? = "",
    var image: String? = "",
    var email: String? = "",
    var city: String? = ""
)