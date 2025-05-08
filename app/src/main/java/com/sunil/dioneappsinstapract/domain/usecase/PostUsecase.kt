package com.sunil.dioneappsinstapract.domain.usecase

import com.sunil.dioneappsinstapract.data.repository.PostRepository
import com.sunil.dioneappsinstapract.domain.model.postRequest
import com.sunil.dioneappsinstapract.domain.model.postResponse
import retrofit2.Response
import javax.inject.Inject

class PostUsecase @Inject constructor(private val postRepository: PostRepository) {

    suspend operator fun invoke(page : Int, request: postRequest) : postResponse{
        return postRepository.getPost(page, request)
    }

}