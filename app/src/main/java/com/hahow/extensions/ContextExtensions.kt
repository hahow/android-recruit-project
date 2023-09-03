package com.hahow.extensions

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build

/**
 * 判斷是否有網路連線
 * */
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            return hasTransport(TRANSPORT_WIFI) || hasTransport(TRANSPORT_CELLULAR)
        }
    } else {
        connectivityManager?.activeNetworkInfo?.run {
            return type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE
        }
    }
    return false
}
