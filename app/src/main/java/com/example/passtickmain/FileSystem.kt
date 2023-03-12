package com.example.passtickmain

import android.os.Environment
import java.io.File

class FileSystem {
    companion object {
        private var externalDir = Environment.getExternalStorageDirectory().toString() + "/.passstick"
        private var externalFilename = "passkeys"
        private var appDir = Environment.getDataDirectory().toString()

        // copy content in appDir to externalDir
        fun update(appFilename: String): Int {
            val inFile  = File("$appDir/$appFilename")
            val outFile = File("$externalDir/$externalFilename")

            try {
                inFile.copyTo(outFile, true)
            } catch (exception: FileSystemException) {
                println("Error: $exception")
                return 1
            }
            return 0
        }

        // check that the external file exists
        fun check(): Boolean {
            val file = File("$externalDir/$externalFilename")
            return file.exists()
        }

        // create externalDir
        fun create(): Int {
            val dir = File(externalDir)

            try {
                dir.mkdirs()
            } catch (exception: FileSystemException) {
                println("Error: $exception")
                return 1
            }
            return 0
        }

        // copy content in externalDir to appDir
        fun load(appFilename: String): Int {
            val outFile  = File("$appDir/$appFilename")
            val inFile = File("$externalDir/$externalFilename")

            try {
                inFile.copyTo(outFile, true)
            } catch (exception: FileSystemException) {
                println("Error: $exception")
                return 1
            }
            return 0
        }
    }
}