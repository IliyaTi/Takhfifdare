package com.example.takhfifdar.data.repositories.local.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room


@Database(entities = [User::class, Token::class], version = 2, exportSchema = false)
abstract class TakhfifdarDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun TokenDao(): TokenDao

    companion object {
        @Volatile
        private var INSTANCE: TakhfifdarDatabase? = null

        fun getDatabase(context: Context): TakhfifdarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TakhfifdarDatabase::class.java,
                    "TakhfifdarDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}