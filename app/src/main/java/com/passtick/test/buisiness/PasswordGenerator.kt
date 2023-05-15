package com.passtick.test.buisiness

import kotlin.random.Random


class PasswordGenerator {

    fun generatePassowrd(Length : Int = 12, includeNumbers: Boolean, includeSymbols: Boolean) : String{

        val charList = mutableListOf<Char>()
        val tempPass = mutableListOf<Char>()
        val symbols = listOf('!', '@', '#', '%', '^', '&', '*', '(', ')', '-', '=', '"', ':', ';', '[', ']', '|', ',', '<', '>', '.', '?', '\\', '\'', '\$')
        val numbers = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        var size = Length

        for(char in 'a' .. 'z')
            charList.add(char)

        for(char in 'A' .. 'Z')
            charList.add(char)

        if(includeNumbers) {
            for (char in numbers) {
                charList.add(char)
            }
            tempPass.add(numbers[Random.nextInt(numbers.size)])
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
        
        return tempPass.joinToString(separator = "")
    }
}