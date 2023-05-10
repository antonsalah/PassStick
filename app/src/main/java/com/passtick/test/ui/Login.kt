package com.passtick.test.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.passtick.test.ui.account.AccountScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation()
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController = navController) }
        composable("account") { AccountScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    // Add other composables for UI elements (e.g., EditText, Button) here

    val passwordEditText = remember { mutableStateOf(TextFieldValue("")) }

    Column {
        TextField(
            value = passwordEditText.value,
            onValueChange = { passwordEditText.value = it },
            label = { Text("Password") }
        )

        Button(onClick = {
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
}

private fun checkPassword(password: String): Boolean {
    // Replace 'your_password' with the desired password
    return password.isNotEmpty()
}
