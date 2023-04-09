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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
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
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountScreen(
    accountList: List<Account>,
    onSave: (account: Account) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        var usernameAccount by remember { mutableStateOf("") }
        var passwordAccount by remember { mutableStateOf("") }
        var serviceNameAccount by remember { mutableStateOf("") }
        val listState = rememberLazyListState()
        val listCoroutineScope = rememberCoroutineScope()

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

            Button(
                modifier = Modifier.width(96.dp),
                onClick = {
                    onSave(
                        Account(
                            usernameAccount,
                            passwordAccount,
                            serviceNameAccount
                        )
                    )
                    listCoroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }) {
                Text("Save")
            }
        }
        PasswordListDisplay(accountList = accountList, state = listState)
    }
}

@Composable
fun AccountDisplay(account: Account) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Text("    ${account.serviceName} ${account.username} ${account.password}    ")
        }
}
@Composable
fun PasswordListDisplay(accountList: List<Account>, state: LazyListState) {

    LazyColumn(state = state) {
        items(items = accountList, key = { it.uid }) { account ->
            AccountDisplay(account = account)
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        AccountScreen(listOf(Account("Compose", "Room", "Kotlin")), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        AccountScreen(listOf(Account("Compose", "Room", "Kotlin")), onSave = {})
    }
}
