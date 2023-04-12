package com.passtick.test.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.passtick.test.data.local.database.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialogue(
    openAddDialogue: MutableState<Boolean>,
    listCoroutineScope: CoroutineScope,
    listState: LazyListState
) {
    val viewModel: AccountViewModel = hiltViewModel()
    var usernameAccount by remember { mutableStateOf("") }
    var passwordAccount by remember { mutableStateOf("") }
    var serviceNameAccount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { openAddDialogue.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.addAccount(
                        Account(
                            usernameAccount,
                            passwordAccount,
                            serviceNameAccount
                        )
                    )
                    usernameAccount = ""
                    passwordAccount = ""
                    serviceNameAccount = ""
                    listCoroutineScope.launch {
                        delay(100)
                        listState.animateScrollToItem(index = 0)
                    }
                    openAddDialogue.value = false
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openAddDialogue.value = false
                }
            ) {
                Text("Exit")
            }
        },
        text = {
            Column{


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = serviceNameAccount,
                        onValueChange = { serviceNameAccount = it },
                        placeholder = { Text(text = "Service Name") }
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = usernameAccount,
                        onValueChange = { usernameAccount = it },
                        placeholder = { Text(text = "Username") }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = passwordAccount,
                        onValueChange = { passwordAccount = it },
                        placeholder = { Text(text = "Password") }
                    )
                }
            }

        }
    )
}