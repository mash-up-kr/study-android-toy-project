package com.example.android_toy_project_study_2020_mvvm.api

import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceInterface {
    @GET("/search/repositories")
    fun requestGithubResponse(
        @Query("q") keyword: String,
        @Query("page") page: Int = 0
    ): Call<GithubResponseData>
}