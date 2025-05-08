package com.sunil.dioneappsinstapract

import android.app.Application
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.sunil.dioneappsinstapract.constants.ConnectionStatus
import com.sunil.dioneappsinstapract.constants.ExoplayerCache
import com.sunil.dioneappsinstapract.constants.NetworkConnectivityObserver
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MyApp : Application() {

    lateinit var networkObserver : NetworkConnectivityObserver

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        ExoplayerCache.init(this)

        networkObserver = NetworkConnectivityObserver(this)
        networkObserver.startObserving()

        observeNetworkChanges()

    }



    private fun observeNetworkChanges() {
        CoroutineScope(Dispatchers.Main).launch{
            networkObserver.status.collect{ status->

                when(status){
                    ConnectionStatus.Available ->{
                        Toast.makeText(this@MyApp,"Internet Available",Toast.LENGTH_SHORT).show()
                    }
                    ConnectionStatus.Unavailable ->{
                        Toast.makeText(this@MyApp,"Internet Unavailable",Toast.LENGTH_SHORT).show()
                    }
                    ConnectionStatus.Lost ->{
                        Toast.makeText(this@MyApp,"Connection Lost",Toast.LENGTH_SHORT).show()
                    }

                    else ->{}
                }

            }
        }
    }
}