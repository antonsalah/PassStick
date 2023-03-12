package com.example.passtickmain

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.passtickmain.ui.theme.PasstickMainTheme
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasstickMainTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
    fun fsExample() {
        val filename = "db.sqlite"

        // verify DB file does not exist in USB storage
        if(!FileSystem.check()) {

            // if so, set up the USB directory
            FileSystem.create()

            // then, create a local DB file in app data
            val db = File(Environment.getDataDirectory().toString() + '/' + filename)
            try {
                db.createNewFile()
            } catch (exception: FileSystemException) {
                println("Error: $exception")
                return
            }

            // without modifying it, copy this new DB file to the USB storage
            FileSystem.update(filename)

            // had we modified, we could then load it back to compare
            FileSystem.load(filename)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PasstickMainTheme {
        Greeting("Android")
    }
}