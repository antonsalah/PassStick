package com.example.passtickmain.data
<<<<<<< HEAD
import androidx.room.Entity
import androidx.room.PrimaryKey
=======

import androidx.room.*
>>>>>>> origin/Data/UI_Architecture

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val serviceName: String,
    var username: String,
    var password: String
)