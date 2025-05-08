package com.sunil.dioneappsinstapract.data.api

import com.sunil.dioneappsinstapract.domain.model.postRequest
import com.sunil.dioneappsinstapract.domain.model.postResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/v2/post/getPost?")
    suspend fun getPost(@Header("Authorization") auth : String,
                        @Query("page") page : Int,
                        @Body request : postRequest
    ) : postResponse
}