package com.example.pythonOnAndroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pythonOnAndroid.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}