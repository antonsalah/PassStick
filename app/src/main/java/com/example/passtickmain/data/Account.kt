package com.example.passtickmain.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val serviceName: String,
    var username: String,
    var password: String
)