package com.example.pythonOnAndroid.activities

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.pythonOnAndroid.databinding.ActivityOptionsBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val editor = sharedPref.edit()
        initOptions(sharedPref)
        val defaultSnakeColor = sharedPref.getString("snakeColorHex", "#00FF00").toString()
        val defaultFoodColor = sharedPref.getString("foodColorHex", "#FF0000").toString()

        binding.optionsThemesRdGroup.setOnCheckedChangeListener { _, checkId ->
            val chosenThemeOption: Int = when (checkId) {
                binding.optionsThemesRbDark.id -> AppCompatDelegate.MODE_NIGHT_YES
                binding.optionsThemesRbLight.id -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(chosenThemeOption)
            editor.apply{
                putInt("chosenCheckBoxTheme",checkId)
                putInt("chosenTheme",chosenThemeOption)
                apply()
            }
        }

        binding.optionsSnakeColorBtn.setOnClickListener {
            displayColorPickerDialog(
                "Pick Snake Color",
                defaultSnakeColor,
                binding.optionsSnakeColorBtn,
                editor,
                "snakeColor",
                "snakeColorHex"
            )
        }

        binding.optionsFoodColorBtn.setOnClickListener {
            displayColorPickerDialog(
                "Pick Food Color",
                defaultFoodColor,
                binding.optionsFoodColorBtn,
                editor,
                "foodColor",
                "foodColorHex"
            )
        }

        binding.optionsControlSensibilitySeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                var sensibility = 2
                if (seekbar != null) {
                    sensibility = seekbar.progress + 1
                }
                editor.apply {
                    putFloat("sensibility", sensibility.toFloat())
                    apply()
                }
                Toast.makeText(
                    this@OptionsActivity,
                    "New Control sensibility is: $sensibility",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        binding.optionsSnakeSpeedSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                var snakeSpeed = 150
                if (seekbar != null) {
                    snakeSpeed = seekbar.progress
                }
                editor.apply {
                    putLong("snakeSpeed", snakeSpeed.toLong())
                    apply()
                }
                Toast.makeText(
                    this@OptionsActivity, "New Snake speed is: $snakeSpeed", Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun displayColorPickerDialog(
        pickerTitle: String,
        defaultColor: String,
        buttonRef: Button,
        editor: SharedPreferences.Editor,
        colorKey: String,
        colorHexKey: String
    ) {
        ColorPickerDialog.Builder(this).setTitle(pickerTitle).setColorShape(ColorShape.CIRCLE)
            .setDefaultColor(defaultColor).setColorListener { color, colorHex ->
                buttonRef.setBackgroundColor(color)
                buttonRef.text = colorHex
                editor.apply {
                    putInt(colorKey, color)
                    putString(colorHexKey, colorHex)
                    apply()
                }
            }.show()
    }

    private fun initOptions(sharedPref: SharedPreferences) {
        //-Style
        //--Themes
        binding.optionsThemesRdGroup.check(sharedPref.getInt("chosenCheckBoxTheme",1))
        //--SnakeColor
        binding.optionsSnakeColorBtn.setBackgroundColor(
            sharedPref.getInt(
                "snakeColor", Color.GREEN
            )
        )
        binding.optionsSnakeColorBtn.text = sharedPref.getString("snakeColorHex", "#00FF00")
        //--FoodColor
        binding.optionsFoodColorBtn.setBackgroundColor(sharedPref.getInt("foodColor", Color.RED))
        binding.optionsFoodColorBtn.text = sharedPref.getString("foodColorHex", "#FF0000")
        //-Game settings
        //--Control sensibility
        binding.optionsControlSensibilitySeekBar.progress =
            sharedPref.getFloat("sensibility", 2F).toInt()
        //--Snake speed
        binding.optionsSnakeSpeedSeekBar.progress = sharedPref.getLong("snakeSpeed", 150L).toInt()
        //-Language
        //Todo implement it Gian-Luca
    }
}