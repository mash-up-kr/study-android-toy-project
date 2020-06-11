package com.example.android_toy_project_study_2020_mvvm.model

interface BaseResponse<T> {

    fun onSuccess(data: T)

    fun onFail(description: Int)

    fun onError(throwable: Throwable)

    fun onLoading()

    fun onLoaded()
}