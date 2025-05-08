package com.sunil.dioneappsinstapract.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunil.dioneappsinstapract.data.Resource
import com.sunil.dioneappsinstapract.domain.model.Data
import com.sunil.dioneappsinstapract.domain.model.postRequest
import com.sunil.dioneappsinstapract.domain.usecase.PostUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val usecase: PostUsecase
) : ViewModel() {


    private val allPosts = mutableListOf<Data>()

    val resource = MutableLiveData<Resource<List<Data>>>()

    fun getPost(page: Int,request: postRequest){

        viewModelScope.launch {
            resource.value = Resource.Loading()
            try {
                val resp = usecase.invoke(page, request)

                val count = resp.totalCount

                if (resp.status.contentEquals("200")) {
                    val data = resp.data
                    allPosts.addAll(data)
                    resource.value = Resource.Success(allPosts, count)
                }else{
                    resource.value = Resource.Error(resp.message)
                }
            }catch (e: Exception){
                resource.value = Resource.Error(e.toString())
            }

        }
    }
}