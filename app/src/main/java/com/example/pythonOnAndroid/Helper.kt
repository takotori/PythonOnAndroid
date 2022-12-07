package com.example.pythonOnAndroid

import android.content.Context
import android.net.ConnectivityManager

class Helper {
    companion object{
        fun isAppOnline(context: Context):Boolean{
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null
        }
    }
}