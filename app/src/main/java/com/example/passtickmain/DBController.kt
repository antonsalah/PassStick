package com.example.passtickmain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")



data class account(
    @PrimaryKey(autoGenerate = true)
    var serviceName: String,
    var userName: String,
    var password: String
)