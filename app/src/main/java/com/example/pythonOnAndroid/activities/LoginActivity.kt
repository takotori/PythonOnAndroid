package com.example.pythonOnAndroid.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences(PreferenceKeys.preferenceName, MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(sharedPref.getInt(PreferenceKeys.chosenTheme, 1))
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isOnline(applicationContext) && Firebase.auth.currentUser == null) {
            createSignInIntent()
        } else {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            Toast.makeText(
                applicationContext,
                resources.getString(R.string.save_speed_toast).format(response),
                Toast.LENGTH_LONG
            ).show()
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