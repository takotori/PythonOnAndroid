package com.example.pythonOnAndroid.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.databinding.ActivityMenuBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", FirebaseUser::class.java)
        } else {
            intent.getParcelableExtra("user") as? FirebaseUser
        }

        if (userIntent == null || userIntent.isAnonymous) {
            binding.logoutBtn.text = "Login"
        }

        setupClickListener()
    }

    private fun setupClickListener() {
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
            if (isOnline(applicationContext)) {
                signOut()
            } else {
                Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
            }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}