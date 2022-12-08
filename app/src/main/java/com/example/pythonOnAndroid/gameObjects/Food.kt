package com.example.pythonOnAndroid.gameObjects

class Food {
    companion object {
        var posX = (1..20).random().toFloat() * 50
        var posY = (1..20).random().toFloat() * 50

        fun generate() {
            posX = (1..20).random().toFloat() * 50
            posY = (1..20).random().toFloat() * 50
        }
    }
}