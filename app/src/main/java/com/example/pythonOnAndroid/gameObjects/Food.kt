package com.example.pythonOnAndroid.gameObjects

class Food {
    companion object {
        var posX = (2..19).random().toFloat() * 50
        var posY = (2..19).random().toFloat() * 50

        fun generate() {
            posX = (2..19).random().toFloat() * 50
            posY = (2..19).random().toFloat() * 50
        }
    }
}