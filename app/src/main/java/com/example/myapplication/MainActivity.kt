package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

