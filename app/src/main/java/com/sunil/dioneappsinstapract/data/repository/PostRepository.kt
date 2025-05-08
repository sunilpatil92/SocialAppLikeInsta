package com.sunil.dioneappsinstapract.data.repository

import com.sunil.dioneappsinstapract.constants.constant
import com.sunil.dioneappsinstapract.data.api.ApiService
import com.sunil.dioneappsinstapract.domain.model.postRequest
import com.sunil.dioneappsinstapract.domain.model.postResponse
import retrofit2.Response
import javax.inject.Inject


class PostRepository @Inject constructor(private  val apiService: ApiService) {
    suspend fun getPost(page : Int, request: postRequest) : postResponse{
        val token = "Bearer "+constant.token
        return apiService.getPost(token,page, request)
    }
}