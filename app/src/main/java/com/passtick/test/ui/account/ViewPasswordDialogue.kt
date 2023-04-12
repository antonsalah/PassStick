package com.passtick.test.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.passtick.test.data.local.database.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordDialogue(
    account: Account,
    openPasswordDialogue: MutableState<Boolean>
) {
    val isInEditMode = remember { mutableStateOf(false) }
    val viewModel: AccountViewModel = hiltViewModel()
    var usernameAccount by remember { mutableStateOf("") }
    var passwordAccount by remember { mutableStateOf("") }
    var serviceNameAccount by remember { mutableStateOf("") }
    usernameAccount = account.username
    passwordAccount = account.password
    serviceNameAccount = account.serviceName

    AlertDialog(
        onDismissRequest = { openPasswordDialogue.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    openPasswordDialogue.value = false
                }
            ) {
                Text("Exit")
            }
        },
        text = {
            if(isInEditMode.value) {
                Column {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){

                        Icon(Icons.Default.AccountBox, null)
                        TextField(
                            value = serviceNameAccount,
                            onValueChange = { serviceNameAccount = it },
                            placeholder = { Text(text = serviceNameAccount) }
                        )
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        Icon(Icons.Default.Person, null)
                        TextField(
                            value = usernameAccount,
                            onValueChange = { usernameAccount = it },
                            placeholder = { Text(text = usernameAccount) }
                        )
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        Icon(Icons.Default.Lock, null)
                        TextField(
                            value = passwordAccount,
                            onValueChange = { passwordAccount = it },
                            placeholder = { Text(text = passwordAccount) }
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.deleteAccount(account)
                        }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    Button(
                        onClick = {
                            if(isInEditMode.value) {
                                viewModel.updateAccount(
                                    account,
                                    usernameAccount,
                                    passwordAccount,
                                    serviceNameAccount,

                                )
                            }
                            isInEditMode.value = !isInEditMode.value
                        }) {
                        Icon(Icons.Default.Check, null)
                    }
                }
            } else {
                Column {
                    Row {
                        Icon(Icons.Default.AccountBox, null)
                        Text("Service: \n \n $serviceNameAccount \n", fontSize = 24.sp)
                    }
                    Row {
                        Icon(Icons.Default.Person, null)
                        Text("Username: \n \n $usernameAccount \n", fontSize = 24.sp)
                    }
                    Row {
                        Icon(Icons.Default.Lock, null)
                        Text("Password: \n \n $passwordAccount \n", fontSize = 24.sp)
                    }
                    Button(
                        onClick = {
                            viewModel.deleteAccount(account)
                        }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    Button(
                        onClick = {
                            if(isInEditMode.value) {
                                viewModel.updateAccount(
                                    account,
                                    usernameAccount,
                                    passwordAccount,
                                    serviceNameAccount,

                                )
                            }
                            isInEditMode.value = !isInEditMode.value
                        }) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
            }
        }
    )
}