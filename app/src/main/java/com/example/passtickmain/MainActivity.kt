package com.example.passtickmain


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passtickmain.data.*
import com.example.passtickmain.ui.theme.Purple200
import com.example.passtickmain.ui.theme.Purple500
import com.example.passtickmain.ui.theme.Purple700
import com.example.passtickmain.ui.theme.Teal200
import kotlinx.coroutines.flow.Flow

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
                        //DisplayPasswordList(database.getDatabase().accountDao().getAllAccounts())
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
                val account2bAdded = Account(usernameInput.value.toInt(), passwordInput.value, siteInput.value)
                val db =AppDatabase.getInstance(context).accountDao()
                db.addAccount(account2bAdded)

            }) {
            Text(text = "Add")
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = {

            }) {
            Text(text = "Initialize")
        }
    }
}
@Composable
fun AddButton() {
    val ctx = LocalContext.current
    val text = "some text"
    Button(onClick = {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show()
    }) {
        Text(text = text)
    }
}


//Given a list of AccountEntry, display each given the
@Composable
fun DisplayPasswordList(entries: Flow<List<Account>>) {
    LazyColumn {
                val db = AppDatabase.getInstance(context).accountDao().getAllAccounts()

        }
    }


//Displays one entry given an AccountEntry
@Composable
fun DisplayEntry(entry: Account){
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Text(text = entry.userName + "   ")
        Text(text = entry.password + "   ")
        Text(text = entry.serviceName)
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