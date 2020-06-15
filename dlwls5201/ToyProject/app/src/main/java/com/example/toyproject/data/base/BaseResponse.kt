package com.example.toyproject.data.base

interface BaseResponse<T> {

    fun onSuccess(data: T)

    fun onFail(description: String)

    fun onError(throwable: Throwable)

    fun onLoading()

    fun onLoaded()
}