package com.example.yatodo.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object ConnectionCheck {
    /**
     * Checks if device is connected to a network and if this network gives access to the internet.
     * Requires [context]
     */
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
