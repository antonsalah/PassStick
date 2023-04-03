package com.example.passtick.data

import androidx.room.*

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val serviceName: String,
    var username: String,
    var password: String
)