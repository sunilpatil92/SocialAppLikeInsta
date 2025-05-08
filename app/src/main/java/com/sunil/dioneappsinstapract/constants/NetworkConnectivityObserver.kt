package com.sunil.dioneappsinstapract.constants

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkConnectivityObserver(private val context: Context) {
    private val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private  val _status = MutableStateFlow(ConnectionStatus.Unavailable)
    val status : StateFlow<ConnectionStatus> = _status

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            _status.value = ConnectionStatus.Available
        }

        override fun onUnavailable() {
            _status.value = ConnectionStatus.Unavailable
        }

        override fun onLost(network: Network) {
            _status.value = ConnectionStatus.Lost
        }
    }

    fun startObserving(){
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request,callback)
    }

   /* fun stopObserving(){
        connectivityManager.unregisterNetworkCallback(callback)
    }*/
}