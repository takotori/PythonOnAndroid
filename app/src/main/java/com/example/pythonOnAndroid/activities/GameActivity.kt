package com.example.pythonOnAndroid.activities

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pythonOnAndroid.Food
import com.example.pythonOnAndroid.Snake
import com.example.pythonOnAndroid.databinding.ActivityGameBinding
import kotlinx.coroutines.*

class GameActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityGameBinding
    private lateinit var sensorManager: SensorManager
    private var movementSensitivity: Float = 2F
    private var snakeSpeed: Long = 150
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        binding.scoreTextView.text = "SCORE: 0"
        setContentView(binding.root)
        setUpSensor()

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (Snake.alive) {
                    moveSnake()
                    Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))
                    if (Snake.headX == Food.posX && Snake.headY == Food.posY) {
                        updateScore(score+1)
                        Food.generate()
                    } else {
                        Snake.bodyParts.removeAt(0)
                    }
                    binding.canvas.invalidate()
                    delay(snakeSpeed)
                }
            }
        }
    }

    private fun updateScore(score: Int){
        this.score = score
        runOnUiThread {
            binding.scoreTextView.text = "SCORE: $score"
        }
    }

    private fun moveSnake() {
        when (Snake.direction) {
            "up" -> {
                Snake.headY -= 50
                checkPossibleMoves()
            }
            "down" -> {
                Snake.headY += 50
                checkPossibleMoves()
            }
            "left" -> {
                Snake.headX -= 50
                checkPossibleMoves()
            }
            "right" -> {
                Snake.headX += 50
                checkPossibleMoves()
            }
        }
    }

    private fun checkPossibleMoves() {
        if (!Snake.possibleMove()) {
            Snake.alive = false
            updateScore(0)
            Snake.reset()
        }
    }

    private fun setUpSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val sides = -event.values[0]
            val upDown = event.values[1]

            if (sides < -movementSensitivity) {
                Snake.alive = true
                if (Snake.direction != "right")
                    Snake.direction = "left"
            }
            if (sides > movementSensitivity) {
                Snake.alive = true
                if (Snake.direction != "left")
                    Snake.direction = "right"
            }
            if (upDown < -movementSensitivity) {
                Snake.alive = true
                if (Snake.direction != "down")
                    Snake.direction = "up"
            }
            if (upDown > movementSensitivity) {
                Snake.alive = true
                if (Snake.direction != "up")
                    Snake.direction = "down"
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}