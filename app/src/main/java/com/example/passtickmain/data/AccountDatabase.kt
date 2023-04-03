package com.example.passtickmain.data

import  androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
<<<<<<< HEAD
import androidx.room.Room
import com.example.passtickmain.accountDao


=======
import androidx.compose.runtime.internal.composableLambdaNInstance
import androidx.room.Room
>>>>>>> origin/Data/UI_Architecture

@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

<<<<<<< HEAD
    abstract fun accountDao(): accountDao

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
=======
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var INSTANCE: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase{
            //if the instance is not null, return it. Otherwise create a new database instance.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, AccountDatabase::class.java, "account_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
>>>>>>> origin/Data/UI_Architecture
            }
        }
    }
}