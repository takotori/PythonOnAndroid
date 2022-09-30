package com.example.pythonOnAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        print("Hello")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}