package com.example.android_toy_project_study_2020_mvvm.model.api

import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInterface {

    @GET("/search/repositories")
    fun requestGithubResponse(
        @Query("q") keyword: String,
        @Query("page") page: Int = 0
    ): Call<GithubResponseData>

    @GET("/repos/{userName}/{repoName}")
    fun requestGetRepository(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Call<GithubDetailRepoData>

    @GET("/users/{userName}")
    fun requestSingleUser(
        @Path("userName") userName: String
    ): Call<GithubDetailUserData>
}