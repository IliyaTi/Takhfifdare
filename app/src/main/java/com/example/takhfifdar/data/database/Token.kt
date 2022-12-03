package com.example.takhfifdar.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class Token(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val token: String
)
