package com.example.android_toy_project_study_2020_mvvm.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

        fun getService(): RetrofitServiceInterface = retrofit.create(RetrofitServiceInterface::class.java)
        private val retrofit =
            Retrofit.Builder()
                .baseUrl("https://api.github.com") // 도메인 주소
                .addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
                .build()


}
