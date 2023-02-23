package com.example.takhfifdar.data.repositories.local.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class, Token::class], version = 4, exportSchema = false)
abstract class TakhfifdarDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun TokenDao(): TokenDao
//    abstract fun TransactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: TakhfifdarDatabase? = null

        val migration3to4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user_table ADD COLUMN invite_code varchar(255);")
                database.execSQL("ALTER TABLE user_table ADD COLUMN parent_invite varchar(255);")
                database.execSQL("ALTER TABLE user_table ADD COLUMN score INTEGER;")
            }
        }
//        val migration4to5 = object : Migration(4, 5) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                TODO("Not yet implemented")
//            }
//        }



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
                )
                    .addMigrations(migration3to4)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}