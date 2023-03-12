package com.example.passtickmain.data

import androidx.room.*

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val serviceName: String,
    var userName: String,
    var password: String
)