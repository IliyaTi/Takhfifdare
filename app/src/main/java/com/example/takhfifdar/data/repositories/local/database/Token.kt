package com.example.takhfifdar.data.repositories.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class Token(
    @PrimaryKey
    val id: Int,
    val token: String
)
