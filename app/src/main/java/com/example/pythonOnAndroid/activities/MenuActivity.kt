package com.example.pythonOnAndroid.activities

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

        binding.optionsBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MenuActivity,
                    OptionsActivity::class.java
                )
            )
        }

        binding.scoreBoardBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MenuActivity,
                    ScoreActivity::class.java
                )
            )
        }
        //Todo change method in inside clickListener
        binding.startGameBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MenuActivity,
                    GameActivity::class.java
                )
            )
        }
        binding.logoutBtn.setOnClickListener {
            signOut()
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