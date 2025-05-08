package com.sunil.dioneappsinstapract.di

import com.sunil.dioneappsinstapract.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://socketapi.tepnot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}
//"https://socketapi.tepnot.com/api/v2/post/getPost?page=1"