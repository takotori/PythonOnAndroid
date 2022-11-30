package com.example.pythonOnAndroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pythonOnAndroid.databinding.ActivityOptionsBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.optionsSnakeColorBtn.setOnClickListener{
            ColorPickerDialog
                .Builder(this)
                .setTitle("Pick Snake Color")
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor("#00FF00")
                .setColorListener { color, colorHex ->
                    binding.optionsSnakeColorBtn.setBackgroundColor(color)
                }
                .show()
        }

        binding.optionsFoodColorBtn.setOnClickListener{
            ColorPickerDialog
                .Builder(this)
                .setTitle("Pick Food Color")
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor("#FF0000")
                .setColorListener { color, colorHex ->
                    binding.optionsFoodColorBtn.setBackgroundColor(color)
                }
                .show()
        }


    }


}