package com.sunil.dioneappsinstapract.data

sealed class Resource<T>(val data: T? = null, val message: String? = null, val count: Int? = null) {
    class Success<T>(data: T,count:Int?=null) : Resource<T>(data,count=count)
    class Error<T>(message: String?) : Resource<T>(message=message)
    class Loading<T>() : Resource<T>()
}