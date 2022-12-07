package com.example.pythonOnAndroid.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.Helper
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.databinding.ActivityMenuBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var offlineUserDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val sharedPref = getSharedPreferences(PreferenceKeys.preferenceName, MODE_PRIVATE)
        val editor = sharedPref.edit()
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null || user.isAnonymous) {
            binding.logoutBtn.text = resources.getString(R.string.loginBtn_txt)
        }

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT

        offlineUserDialog = AlertDialog.Builder(this)
            .setTitle("Type in a Username")
            .setView(input)
            .setPositiveButton("Start") { _, _ ->
                if (input.text.toString() != "") {
                    editor.apply {
                        putString("userName", input.text.toString())
                        apply()
                    }
                    startGame()
                } else {
                    Toast.makeText(this@MenuActivity, "Add user Name", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancle") { _, _ ->
                offlineUserDialog.cancel()
            }.create()
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

        binding.startGameBtn.setOnClickListener {
            if (Helper.isAppOnline(applicationContext)) {
                startGame()
            } else {
                offlineUserDialog.show()
            }
        }

        if (!Helper.isAppOnline(applicationContext)) {
            binding.logoutBtn.isEnabled = false
            binding.offlineInfoTxt.text = resources.getString(R.string.offline_info_message_menu)
        } else {
            binding.offlineInfoTxt.text = ""
        }

        binding.logoutBtn.setOnClickListener {
            if (Helper.isAppOnline(applicationContext)) {
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

    private fun startGame() {
        startActivity(
            Intent(
                this@MenuActivity,
                GameActivity::class.java
            )
        )
    }
}