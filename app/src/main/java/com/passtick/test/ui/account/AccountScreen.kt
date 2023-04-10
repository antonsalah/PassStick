/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.passtick.test.ui.account

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.passtick.test.ui.theme.MyApplicationTheme
import com.passtick.test.data.local.database.Account
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(modifier: Modifier = Modifier, viewModel: AccountViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<AccountUiState>(
        initialValue = AccountUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is AccountUiState.Success) {
        AccountScreen(
            accountList = (items as AccountUiState.Success).data,
            onSave = viewModel::addAccount,
            onDelete = viewModel::deleteAccount,
            modifier = modifier
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountScreen(
    accountList: List<Account>,
    onSave: (account: Account) -> Unit,
    onDelete: (account: Account) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val listCoroutineScope = rememberCoroutineScope()
    val openAddDialogue = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAddDialogue.value = true
                },
                containerColor = Color(0x00,0x99,0xcc, 0xff),
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
       //floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            if (openAddDialogue.value) {
                AddAccountDialogue(
                    onSave = onSave,
                    openAddDialogue = openAddDialogue,
                    listCoroutineScope = listCoroutineScope,
                    listState = listState
                )
            }
            PasswordListDisplay(accountList = accountList, state = listState, onDelete, onSave)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountDialogue(
    onSave: (account: Account) -> Unit,
    openAddDialogue: MutableState<Boolean>,
    listCoroutineScope: CoroutineScope,
    listState: LazyListState
) {
    var usernameAccount by remember { mutableStateOf("") }
    var passwordAccount by remember { mutableStateOf("") }
    var serviceNameAccount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { openAddDialogue.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordDialogue(account: Account, openPasswordDialogue: MutableState<Boolean>, onDelete: (account: Account) -> Unit, onSave: (account: Account) -> Unit,) {
    val isInEditMode = remember { mutableStateOf(false) }
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
                            onDelete(account)
                        }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    Button(
                        onClick = {
                            onModify(isInEditMode, account, onDelete, onSave, serviceNameAccount, usernameAccount, passwordAccount)
                        }) {
                        Icon(Icons.Default.Check, null)
                    }
                }
            } else {
                Column {
                    Row {
                        Icon(Icons.Default.AccountBox, null)
                        Text("Service: \n \n ${serviceNameAccount} \n", fontSize = 24.sp)
                    }
                    Row {
                        Icon(Icons.Default.Person, null)
                        Text("Username: \n \n ${usernameAccount} \n", fontSize = 24.sp)
                    }
                    Row {
                        Icon(Icons.Default.Lock, null)
                        Text("Password: \n \n ${passwordAccount} \n", fontSize = 24.sp)
                    }
                    Button(
                        onClick = {
                            onDelete(account)
                        }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    Button(
                        onClick = {
                            onModify(isInEditMode, account, onDelete, onSave, serviceNameAccount, usernameAccount, passwordAccount)
                        }) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
            }
        }
    )
}

fun onModify(
    isInEditMode: MutableState<Boolean>,
    account: Account,
    onDelete: (account: Account) -> Unit,
    onSave: (account: Account) -> Unit,
    serviceNameAccount: String,
    usernameAccount: String,
    passwordAccount: String,) {
    if(isInEditMode.value) {
        onDelete(account)
        onSave(
            Account(
                usernameAccount,
                passwordAccount,
                serviceNameAccount,
            )
        )
    }
    isInEditMode.value = !isInEditMode.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDisplay(account: Account, onDelete: (account: Account) -> Unit, onSave: (account: Account) -> Unit,) {
    val openPasswordDialogue = remember { mutableStateOf(false) }
    if (openPasswordDialogue.value) {
        ViewPasswordDialogue(account = account, openPasswordDialogue, onDelete, onSave)
    }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            onClick = {
                openPasswordDialogue.value = true
            },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(10f)) {
                    Row {
                        Icon(Icons.Default.AccountBox, null)
                        Text(
                            " Service: ${account.serviceName}    ",
                        )
                    }
                    Row {
                        Icon(Icons.Default.Person, null)
                        Text(
                            " Username: ${account.username}    ",
                        )
                    }
                }
                /*
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight())
                Column {
                    Button(
                        onClick = {
                            onDelete(account)
                        }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    Button(
                        onClick = {
                            openPasswordDialogue.value = true
                        }) {
                        Icon(Icons.Default.Info, null)
                    }
                }

                 */
            }
        }
}
@Composable
fun PasswordListDisplay(accountList: List<Account>, state: LazyListState, onDelete: (account: Account) -> Unit, onSave: (account: Account) -> Unit,) {

    LazyColumn(state = state) {
        items(items = accountList, key = { it.uid }) { account ->
            AccountDisplay(account = account, onDelete, onSave)
        }
    }
}
// Previews
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        AccountScreen(listOf(Account("Compose", "Room", "Kotlin")), onSave = {}, onDelete = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        AccountScreen(listOf(Account("Compose", "Room", "Kotlin")), onSave = {}, onDelete = {})
    }
}
