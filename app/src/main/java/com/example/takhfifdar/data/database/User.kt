package com.example.takhfifdar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val phone: String,
    val image: String,
    val email: String
)