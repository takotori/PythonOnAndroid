package com.example.pythonOnAndroid.activities.game

import android.view.View
import android.util.AttributeSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import com.example.pythonOnAndroid.gameObjects.Food
import com.example.pythonOnAndroid.gameObjects.Snake

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val snakeBody = Paint()
    private val food = Paint()
    private val level = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val sharedPref =
            context.getSharedPreferences("appPreferences", AppCompatActivity.MODE_PRIVATE)
        snakeBody.color = sharedPref.getInt("snakeColor", Color.GREEN)
        food.color = sharedPref.getInt("foodColor", Color.RED)
        level.color = Color.DKGRAY

        canvas?.drawRect(0f, 0f, 1050f, 1050f, level)

        for (i in Snake.bodyParts) {
            canvas?.drawRect(i[0], i[1], i[0] + 45, i[1] + 45, snakeBody)
        }

        canvas?.drawRect(Food.posX, Food.posY, Food.posX + 45, Food.posY + 45, food)
    }
}