package com.example.pythonOnAndroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pythonOnAndroid.Food
import com.example.pythonOnAndroid.Snake
import com.example.pythonOnAndroid.databinding.ActivityGameBinding
import kotlinx.coroutines.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // move the snake
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (Snake.alive) {
                    when (Snake.direction) {
                        "up" -> {
                            // create new head position
                            Snake.headY -= 50
                            checkPossibleMoves()
                        }
                        "down" -> {
                            // create new head position
                            Snake.headY += 50
                            checkPossibleMoves()
                        }
                        "left" -> {
                            // create new head position
                            Snake.headX -= 50
                            checkPossibleMoves()
                        }
                        "right" -> {
                            // create new head position
                            Snake.headX += 50
                            checkPossibleMoves()
                        }
                    }
                    // convert head to body
                    Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))

                    // delete tail if not eat
                    if (Snake.headX == Food.posX && Snake.headY == Food.posY)
                        Food.generate()
                    else
                        Snake.bodyParts.removeAt(0)

                    //game speed in millisecond
                    binding.canvas.invalidate()
                    delay(150)
                }
            }
        }

        setupListeners()
    }

    private fun checkPossibleMoves() {
        if (!Snake.possibleMove()) {
            Snake.alive = false
            Snake.reset()
        }
    }

    private fun setupListeners() {
        binding.buttonUp.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "down")
                Snake.direction = "up"
        }
        binding.buttonDown.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "up")
                Snake.direction = "down"
        }
        binding.buttonLeft.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "right")
                Snake.direction = "left"
        }
        binding.buttonRight.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "left")
                Snake.direction = "right"
        }
    }
}