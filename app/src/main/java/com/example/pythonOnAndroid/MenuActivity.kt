package com.example.pythonOnAndroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Todo change method in inside clickListener
        binding.optionsBtn.setOnClickListener { binding.optionsBtn.text = "Clicked" }
        binding.scoreBoardBtn.setOnClickListener { binding.scoreBoardBtn.text = "Clicked" }
        binding.startGameBtn.setOnClickListener { binding.startGameBtn.text = "Clicked" }
        binding.logoutBtn.setOnClickListener { binding.logoutBtn.text = "Clicked" }
    }
}