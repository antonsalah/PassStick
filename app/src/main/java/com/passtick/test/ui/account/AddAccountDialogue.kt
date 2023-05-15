package com.passtick.test.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.passtick.test.data.local.database.Account
import com.passtick.test.buisiness.PasswordGenerator
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

    val passGen = PasswordGenerator()
    var includeNums by remember { mutableStateOf(false)}
    var includeSymbs by remember { mutableStateOf(false)}
    var passLength = 12
    var sliderPosition by remember {mutableStateOf(0f)}

    AlertDialog(
        onDismissRequest = { openAddDialogue.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    if(usernameAccount == "" || passwordAccount == "" || serviceNameAccount == ""){
                        return@TextButton
                    }
                    else{
                        viewModel.addAccount(
                            Account(
                                usernameAccount,
                                passwordAccount,
                                serviceNameAccount
                            )
                        )
                    }
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

                Button(
                    onClick = {
                        passwordAccount = passGen.generatePassowrd(Length = passLength, includeSymbols = includeSymbs, includeNumbers = includeNums)
                    }
                ) {
                    Text("Generate Password")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Length", modifier = Modifier.padding(top = 12.dp)
                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it
                                          passLength = sliderPosition.toInt()
                                        },
                        modifier = Modifier
                            .semantics { contentDescription = "Password Length" }
                            .width(150.dp),
                        valueRange = 12f..40f,
                        enabled = true
                    )

                    Text("$passLength", modifier = Modifier.padding(top = 12.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Numbers", modifier = Modifier.padding(top = 12.dp))
                    Checkbox(
                        checked = includeNums,
                        onCheckedChange = { includeNums = !includeNums }
                    )

                    Text("Symbols", modifier = Modifier.padding(top = 12.dp))
                    Checkbox(
                        checked = includeSymbs,
                        onCheckedChange = { includeSymbs = !includeSymbs }
                    )
                }





            }
        }
        )
}