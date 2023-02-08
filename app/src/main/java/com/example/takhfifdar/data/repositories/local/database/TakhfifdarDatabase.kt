package com.example.takhfifdar.data.repositories.local.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class, Token::class], version = 3, exportSchema = false)
abstract class TakhfifdarDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun TokenDao(): TokenDao

    companion object {
        @Volatile
        private var INSTANCE: TakhfifdarDatabase? = null

        val migration3to4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD invite_code varchar(255);")
//                database.execSQL("ALTER TABLE user ADD invite_active bool")
            }
        }

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
                    .addMigrations()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}