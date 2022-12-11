package com.example.takhfifdar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val credit: String = "",
    val image: String = "",
    val email: String = ""
)