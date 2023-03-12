package com.example.passtickmain.data

import  androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var Instance: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase{
            //if the instance is not null, return it. Otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AccountDatabase::class.java, "account_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
    }
}