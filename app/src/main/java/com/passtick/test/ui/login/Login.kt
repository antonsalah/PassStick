package com.passtick.test.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    val passwordEditText = remember { mutableStateOf(TextFieldValue("")) }


    Column() {

        Spacer(modifier = Modifier.height(200.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
    ) {
    Column {
        TextField(
            value = passwordEditText.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, top = 24.dp),
            onValueChange = { passwordEditText.value = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),

            onClick = {
            val password = passwordEditText.value.text
            if (checkPassword(password)) {
                navController.navigate("account")
            } else {
                Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Login")
        }
    }
} }
    }
}

private fun checkPassword(password: String): Boolean {
    return password == "CoolPassword"
}