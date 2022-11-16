package com.example.pythonOnAndroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pythonOnAndroid.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}