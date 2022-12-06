package com.example.pythonOnAndroid.activities

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.databinding.ActivityOptionsBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(PreferenceKeys.preferenceName, MODE_PRIVATE)
        val editor = sharedPref.edit()
        initOptions(sharedPref)
        val defaultSnakeColor =
            sharedPref.getString(PreferenceKeys.snakeColorHex, "#00FF00").toString()
        val defaultFoodColor =
            sharedPref.getString(PreferenceKeys.foodColorHex, "#FF0000").toString()

        binding.optionsThemesRdGroup.setOnCheckedChangeListener { _, checkId ->
            val chosenThemeOption: Int = when (checkId) {
                binding.optionsThemesRbDark.id -> AppCompatDelegate.MODE_NIGHT_YES
                binding.optionsThemesRbLight.id -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(chosenThemeOption)
            editor.apply {
                putInt(PreferenceKeys.chosenCheckBoxTheme, checkId)
                putInt(PreferenceKeys.chosenTheme, chosenThemeOption)
                apply()
            }
        }

        binding.optionsSnakeColorBtn.setOnClickListener {
            displayColorPickerDialog(
                resources.getString(R.string.snake_color_picker_title),
                defaultSnakeColor,
                binding.optionsSnakeColorBtn,
                editor,
                PreferenceKeys.snakeColor,
                PreferenceKeys.snakeColorHex
            )
        }

        binding.optionsFoodColorBtn.setOnClickListener {
            displayColorPickerDialog(
                resources.getString(R.string.food_color_picker_title),
                defaultFoodColor,
                binding.optionsFoodColorBtn,
                editor,
                PreferenceKeys.foodColor,
                PreferenceKeys.foodColorHex
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
                    putFloat(PreferenceKeys.sensibility, sensibility.toFloat())
                    apply()
                }
                Toast.makeText(
                    this@OptionsActivity,
                    resources.getString(R.string.save_sensibility_toast).format(sensibility),
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
                    putLong(PreferenceKeys.snakeSpeed, 300L - snakeSpeed.toLong())
                    apply()
                }
                Toast.makeText(
                    this@OptionsActivity,
                    resources.getString(R.string.save_speed_toast).format(snakeSpeed),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        binding.optionsLanguageDropDown.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val locale = adapterView?.getItemAtPosition(position).toString()
                editor.apply {
                    putInt("locale", position)
                    apply()
                }
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
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
        binding.optionsThemesRdGroup.check(sharedPref.getInt(PreferenceKeys.chosenCheckBoxTheme, 1))
        //--SnakeColor
        binding.optionsSnakeColorBtn.setBackgroundColor(
            sharedPref.getInt(
                PreferenceKeys.snakeColor, Color.GREEN
            )
        )
        binding.optionsSnakeColorBtn.text =
            sharedPref.getString(PreferenceKeys.snakeColorHex, "#00FF00")
        //--FoodColor
        binding.optionsFoodColorBtn.setBackgroundColor(
            sharedPref.getInt(
                PreferenceKeys.foodColor,
                Color.RED
            )
        )
        binding.optionsFoodColorBtn.text =
            sharedPref.getString(PreferenceKeys.foodColorHex, "#FF0000")
        //-Game settings
        //--Control sensibility
        binding.optionsControlSensibilitySeekBar.progress =
            sharedPref.getFloat(PreferenceKeys.sensibility, 2F).toInt()
        //--Snake speed
        binding.optionsSnakeSpeedSeekBar.progress =
            300 - sharedPref.getLong(PreferenceKeys.snakeSpeed, 150L).toInt()
        //-Language
        binding.optionsLanguageDropDown.setSelection(sharedPref.getInt("locale", 0))
    }
}