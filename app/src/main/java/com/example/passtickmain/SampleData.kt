package com.example.passtickmain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.passtickmain.data.*
import java.security.KeyStore.Entry

object SampleData {
    // Sample conversation data
    var passwordListSample by mutableStateOf(listOf(
        Account(
            1,
            "Service1",
            "Username1",
            "Password1",
        ),
        Account(
            2,
            "Service2",
            "Username2",
            "Password2",
        ),
        Account(
            3,
            "Service3",
            "Username3",
            "Password3",
        ),
    )
    )
}