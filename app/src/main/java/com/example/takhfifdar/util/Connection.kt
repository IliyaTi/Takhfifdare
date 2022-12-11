package com.example.takhfifdar.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object Connection {
    sealed class ConnectionState {
        object Available : ConnectionState()
        object Unavailable : ConnectionState()
    }

    val Context.currentConnectivityState: ConnectionState
        get() {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return getCurrentConnectivityState(connectivityManager)
        }

    private fun getCurrentConnectivityState(
        connectivityManager: ConnectivityManager
    ): ConnectionState {
        val connected = connectivityManager.allNetworks.any { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }

        return if (connected) ConnectionState.Available else ConnectionState.Unavailable
    }

    @ExperimentalCoroutinesApi
    fun Context.observeConnectivityAsFlow() = callbackFlow {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = NetworkCallback { connectionState -> trySend(connectionState) }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        // Set current state
        val currentState = getCurrentConnectivityState(connectivityManager)
        trySend(currentState)

        // Remove callback when not used
        awaitClose {
            // Remove listeners
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                callback(ConnectionState.Available)
            }

            override fun onLost(network: Network) {
                callback(ConnectionState.Unavailable)
            }
        }
    }
    @ExperimentalCoroutinesApi
    @Composable
    fun connectivityState(): State<ConnectionState> {
        val context = LocalContext.current

        // Creates a State<ConnectionState> with current connectivity state as initial value
        return produceState(initialValue = context.currentConnectivityState) {
            // In a coroutine, can make suspend calls
            context.observeConnectivityAsFlow().collect { value = it }
        }
    }

    @ExperimentalCoroutinesApi
    @Composable
    fun ConnectivityStatus() {
        // This will cause re-composition on every network state change
        val connection by connectivityState()

        val isConnected = connection === ConnectionState.Available

        if (isConnected) {
            // Show UI when connectivity is available
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Green), horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.Wifi, contentDescription = "")
                Text(text = "Connected")
            }
        } else {
            // Show UI for No Internet Connectivity
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Red), horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.Cloud, contentDescription = "")
                Text(text = "Not Available")
            }
        }
    }
}

