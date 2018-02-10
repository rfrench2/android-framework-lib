package info.anodsplace.framework.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

class NetworkConnectivity(private val context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isNetworkAvailable: Boolean
        get() = connectivityManager.activeNetworkInfo?.isConnected == true

    val isWifiEnabled: Boolean
        get() = connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI

    fun register(broadcastReceiver: NetworkConnectivityChange) {
        context.registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    fun unregister(broadcastReceiver: NetworkConnectivityChange) {
        context.unregisterReceiver(broadcastReceiver)
    }

    class NetworkConnectivityChange(private val listener: Listener): BroadcastReceiver() {

        interface Listener {
            fun onNetworkConnectivity(available: Boolean)
        }

        override fun onReceive(context: Context, intent: Intent?) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            listener.onNetworkConnectivity(connectivityManager.activeNetworkInfo?.isConnected == true)
        }
    }
}