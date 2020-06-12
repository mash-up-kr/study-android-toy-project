package com.example.toyproject2020mvvm.model

interface BaseResponse<T> {

    fun onSuccess(data: T)

    fun onFail(description: Int)

    fun onError(throwable: Throwable)

    fun onLoading()

    fun onLoaded()
}