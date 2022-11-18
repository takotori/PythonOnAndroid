package com.example.pythonOnAndroid

class Snake {
    companion object {
        var headX = 0f
        var headY = 0f
        var bodyParts = mutableListOf(arrayOf(0f, 0f))
        var direction = "right"
        var alive = false

        fun possibleMove(): Boolean {
            for(bodyPart in bodyParts){
                if(bodyPart[0] == headX && bodyPart[1] == headY){
                    return false
                }
            }
            return !(headX < 0f || headX > 1000f || headY < 0f || headY > 1000)
        }

        fun reset() {
            headX = 0f
            headY = 0f
            bodyParts = mutableListOf(arrayOf(0f, 0f))
            direction = "right"
        }
    }
}