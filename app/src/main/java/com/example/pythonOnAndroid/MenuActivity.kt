package com.example.pythonOnAndroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.databinding.ActivityMenuBinding
import com.firebase.ui.auth.AuthUI

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Todo change method in inside clickListener
        binding.optionsBtn.setOnClickListener { binding.optionsBtn.text = "Clicked" }
        binding.scoreBoardBtn.setOnClickListener { binding.scoreBoardBtn.text = "Clicked" }
        binding.startGameBtn.setOnClickListener { binding.startGameBtn.text = "Clicked" }
        binding.logoutBtn.setOnClickListener {
            signOut();
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
            }
    }
}