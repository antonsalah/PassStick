package com.passtick.test.buisiness

import java.security.SecureRandom
import kotlin.random.Random


class PasswordGenerator {

    fun generatePassowrd(Length : Int = 12, includeNumbers: Boolean, includeSymbols: Boolean) : String{

        val charList = mutableListOf<Char>()
        val tempPass = mutableListOf<Char>()
        val symbols = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '-', '"', ':', ';', '[', ']', '\\', '\'', '|', ',', '<', '>', '.', '?')
        var password : String = ""
        var size = Length
        var random = Random.nextInt(charList.size)


        for(char in 'a' .. 'z')
            charList.add(char)

        for(char in 'A' .. 'Z')
            charList.add(char)



        if(includeNumbers) {
            for (char in '0'..'9') {
                charList.add(char)
            }
            tempPass.add(Random.nextInt(10).toChar())
            size--
        }

        if(includeSymbols){
            for (char in symbols){
                charList.add(char)
            }
           tempPass.add(symbols[Random.nextInt(symbols.size)])
            size--
        }


        repeat(size){
           tempPass.add(charList[Random.nextInt(charList.size)])
        }

        tempPass.shuffle()
        password = tempPass.toString()

        return password
    }
}