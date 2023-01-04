package com.example.takhfifdar.data.repositories.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getUser(): User?

    @Query("DELETE from user_table")
    suspend fun deleteUsers()

    @Update
    suspend fun updateUser(user: User)

}