package com.example.task.utils

import android.content.Context
import android.net.ConnectivityManager

object Utility {

    const val otherErr = "Error Occurred!"


    fun isNetWorkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}