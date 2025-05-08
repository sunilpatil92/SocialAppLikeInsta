package com.sunil.dioneappsinstapract.constants

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File


@UnstableApi
object  ExoplayerCache{
    lateinit var simpleCache: SimpleCache
    private const val CACHE_SIZE: Long = 100*1024*1024  //100MB

    @UnstableApi
    fun init(context: Context){
        val cacheDir = File(context.cacheDir, "dioneApp_cache")
        simpleCache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(CACHE_SIZE),
            StandaloneDatabaseProvider(context)
        )
    }

    @OptIn(UnstableApi::class)
    fun getCacheDataSourceFactory(context: Context) : DataSource.Factory{
        val defaulFectory = DefaultDataSource.Factory(context)
        return CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(defaulFectory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

}
