package com.example.android_toy_project_study_2020_mvvm.model.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    fun getService(): RetrofitServiceInterface =
        retrofit.create(RetrofitServiceInterface::class.java)

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}
