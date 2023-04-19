package com.passtick.test.ui.account

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.passtick.test.data.local.database.Account


@Composable
fun ConfirmDeleteDialogue(
    account: Account,
    openDeleteDialogue: MutableState<Boolean>
) {
    val viewModel: AccountViewModel = hiltViewModel()

    AlertDialog(
        onDismissRequest = { openDeleteDialogue.value = false } ,
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteAccount(account)
                    openDeleteDialogue.value = false
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openDeleteDialogue.value = false
                }
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text("Confirm delete")
        }
    )
}