package com.example.takhfifdar.data.repositories.local.database
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface TransactionDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(transaction: Transaction)
//
//    @Query("SELECT * FROM transaction_history")
//    suspend fun getTransactions(): List<Transaction>
//
//
//}