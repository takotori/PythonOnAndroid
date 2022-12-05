package com.example.pythonOnAndroid.activities

import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.Food
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.Snake
import com.example.pythonOnAndroid.databinding.ActivityGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity(), SensorEventListener, GameCallback {
    private lateinit var binding: ActivityGameBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var endScreenFragment: Endscreen
    private var movementSensitivity: Float = 2F
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        binding.scoreTextView.text = resources.getString(R.string.score_place_holder_txt).format(0)
        val sharedPref = getSharedPreferences(PreferenceKeys.preferenceName, MODE_PRIVATE)
        movementSensitivity = sharedPref.getFloat(PreferenceKeys.sensibility, 2F)

        setContentView(binding.root)
        setUpSensor()
        // todo remove score
        endScreenFragment = Endscreen.newInstance(score)

        supportFragmentManager.beginTransaction()
            .add(binding.endScreenFragment.id, endScreenFragment)
            .hide(endScreenFragment)
            .commit()

        moveSnake(sharedPref)
    }

    private fun moveSnake(sharedPref: SharedPreferences) {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (Snake.alive) {
                    moveSnake()
                    Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))
                    if (Snake.headX == Food.posX && Snake.headY == Food.posY) {
                        updateScore(score + 1)
                        Food.generate()
                    } else {
                        Snake.bodyParts.removeAt(0)
                    }
                    binding.canvas.invalidate()
                    delay(sharedPref.getLong(PreferenceKeys.snakeSpeed, 150L))
                }
            }
        }
    }

    private fun updateScore(score: Int) {
        this.score = score
        runOnUiThread {
            binding.scoreTextView.text =
                resources.getString(R.string.score_place_holder_txt).format(score)
        }
    }

    private fun checkPossibleMoves() {
        if (!Snake.possibleMove()) {
            Snake.alive = false
            endScreenFragment.score = score
            supportFragmentManager.beginTransaction()
                .show(endScreenFragment)
                .commitAllowingStateLoss()

            updateScoreOnDB()
        }
    }

    private fun updateScoreOnDB() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && !user.isAnonymous) {
            var foundUser = false
            val url = getString(R.string.dbURL)
            FirebaseDatabase.getInstance(url).getReference("leaderboard").get()
                .addOnSuccessListener {
                    it.children.forEach { data ->
                        if (data.key == user.displayName) {
                            foundUser = true
                            if (score > data.value as Long) {
                                FirebaseDatabase.getInstance(url).getReference("leaderboard")
                                    .child(user.displayName.toString()).setValue(score)
                            }
                        }
                    }
                }
            if (!foundUser) {
                FirebaseDatabase.getInstance(url).getReference("leaderboard")
                    .child(user.displayName.toString()).setValue(score)
            }
        }
    }

    override fun restartGame() {
        supportFragmentManager.beginTransaction()
            .hide(endScreenFragment)
            .commit()
        updateScore(0)
        Snake.reset()
    }

    override fun quitGame() {
        supportFragmentManager.beginTransaction()
            .hide(endScreenFragment)
            .commit()
        startActivity(
            Intent(
                this@GameActivity,
                MenuActivity::class.java
            )
        )
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