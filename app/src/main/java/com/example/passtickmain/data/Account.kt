package com.example.passtickmain.data

import androidx.room.*

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "service_name") val service_Name: String
)