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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    val viewModel: AccountViewModel = hiltViewModel()
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
        AccountDisplayList(
            modifier = modifier
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountDisplayList(
    modifier: Modifier = Modifier
) {
    val viewModel: AccountViewModel = hiltViewModel()
    val listState = rememberLazyListState()
    val listCoroutineScope = rememberCoroutineScope()
    val openAddDialogue = remember { mutableStateOf(false) }
    viewModel.updateQuery("") // initial query populates empty accountList

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
    ) {
        if (openAddDialogue.value) {
            AddAccountDialogue(
                openAddDialogue = openAddDialogue,
                listCoroutineScope = listCoroutineScope,
                listState = listState,
            )
        }
        LazyColumn(state = listState) {
            item {
                Row(modifier) {
                    SearchView()
                }
            }
            items(items = viewModel.filteredAccounts.value, key = { it.uid }) { account ->
                AccountDisplay(account = account)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView() {
    var viewModel: AccountViewModel = hiltViewModel()

    TextField(
        value = viewModel.textState.value,
        onValueChange = { value ->
            viewModel.updateQuery(value)
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (viewModel.textState.value != TextFieldValue("").text) {
                IconButton(
                    onClick = {
                        viewModel.updateQuery("")// Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDisplay(account: Account) {
    val openPasswordDialogue = remember { mutableStateOf(false) }
    if (openPasswordDialogue.value) {
        ViewPasswordDialogue(account = account, openPasswordDialogue)
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
                        Icon(Icons.Default.AccountBox, "Service Name")
                        Text(
                            " Service: ${account.serviceName}    ",
                        )
                    }
                    Row {
                        Icon(Icons.Default.Person, "Username")
                        Text(
                            " Username: ${account.username}    ",
                        )
                    }
                }
            }
        }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        AccountScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        AccountScreen()
    }
}
