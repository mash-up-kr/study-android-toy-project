package com.example.toyproject2020mvvm.model.api

import com.example.toyproject2020mvvm.model.data.GithubDetailRepoData
import com.example.toyproject2020mvvm.model.data.GithubDetailUserData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInterface {

    @GET("/search/repositories")
    fun requestGithubResponse(
        @Query("q") keyword: String,
        @Query("page") page: Int = 0
    ): Single<GithubResponseData>

    @GET("/repos/{userName}/{repoName}")
    fun requestGetRepository(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Single<GithubDetailRepoData>

    @GET("/users/{userName}")
    fun requestSingleUser(
        @Path("userName") userName: String
    ): Single<GithubDetailUserData>
}