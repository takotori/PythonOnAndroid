package com.example.pythonOnAndroid

import android.content.Context
import android.net.ConnectivityManager
import java.math.RoundingMode
import java.text.DecimalFormat

class Helper {
    companion object {
        fun isAppOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null
        }

        fun roundToTwoDecimals(number:Double): Double{
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return df.format(number).toDouble()
        }
    }
}