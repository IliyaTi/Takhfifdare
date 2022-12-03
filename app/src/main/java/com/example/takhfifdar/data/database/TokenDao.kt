package com.example.takhfifdar.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToken(token: String)

    @Query("SELECT * FROM token_table ORDER BY id DESC LIMIT 1")
    suspend fun getToken(): Token
}