package com.example.passtickmain.ui.theme

import com.example.passtickmain.data.Account

data class AccountUiState(
    val id: Int = 0,
    val userName: String = "",
    val password: String = "",
    val serviceName: String = ""
)


fun AccountUiState.toAccount(): Account = Account(
    id = id,
    userName = userName,
    password = password,
    serviceName = serviceName
)

/**
 * Extension function to convert [Account] to [AccountUiState]
 */
fun Account.toItemUiState(actionEnabled: Boolean = false): AccountUiState = AccountUiState(
    id = id,
    userName = userName,
    password = password,
    serviceName = serviceName,
)

fun AccountUiState.isValid() : Boolean {
    return userName.isNotBlank() && password.isNotBlank() && serviceName.isNotBlank()
}
