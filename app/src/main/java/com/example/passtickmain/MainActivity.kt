package com.example.passtickmain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import com.example.passtickmain.ui.theme.Purple200
import com.example.passtickmain.ui.theme.Purple500
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import com.example.passtickmain.ui.theme.Teal200
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.Typography
import com.example.passtickmain.ui.theme.Purple700
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.mutableStateOf
import android.widget.Toast
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

//Values of color palates
private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

//Creates UI Theme
@Composable
fun PasstickUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

data class AccountEntry(val username: String, val password: String, val siteName: String)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasstickUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        AddMenu()
                        Spacer(modifier = Modifier.padding(10.dp))
                        DisplayPasswordList(SampleData.passwordListSample)
                    }
                }
            }
        }
    }
}

@Composable
fun AddMenu() {
    val usernameInput = rememberSaveable { mutableStateOf("Username") }
    val passwordInput = rememberSaveable { mutableStateOf("Password") }
    val siteInput = rememberSaveable { mutableStateOf("Site") }
    val ctx = LocalContext.current

    TextField (
        value = usernameInput.value,
        onValueChange = { newValue ->
            usernameInput.value = newValue }
    )
    Spacer(modifier = Modifier.padding(10.dp))
    TextField (
        value = passwordInput.value,
        onValueChange = { newValue ->
            passwordInput.value = newValue }
    )
    Spacer(modifier = Modifier.padding(10.dp))
    TextField (
        value = siteInput.value,
        onValueChange = { newValue ->
            siteInput.value = newValue }
    )
    Spacer(modifier = Modifier.padding(10.dp))
    Row {
        Button(
            onClick = {
                Toast.makeText(ctx, "Added ${usernameInput.value} ${passwordInput.value} ${siteInput.value}", Toast.LENGTH_LONG).show()
                val newEntry = AccountEntry(usernameInput.value, passwordInput.value, siteInput.value)
                SampleData.passwordListSample += newEntry
            }) {
            Text(text = "Add")
        }
        Spacer(modifier = Modifier.padding(5.dp))

    }
}


//Given a list of AccountEntry, display each given the
@Composable
fun DisplayPasswordList(entries: List<AccountEntry>) {
    LazyColumn {
        items(entries) { entry ->
            DisplayEntry(entry)
        }
    }
}

//Displays one entry given an AccountEntry
@Composable
fun DisplayEntry(entry: AccountEntry) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Text(text = entry.username + "   ")
        Text(text = entry.password + "   ")
        Text(text = entry.siteName)
    }
}

//Preview function for DisplayPasswordList
@Preview(showBackground = true)
@Composable
fun PreviewDisplayPasswordList() {
    PasstickUITheme {
        DisplayPasswordList(entries = SampleData.passwordListSample)
    }
}