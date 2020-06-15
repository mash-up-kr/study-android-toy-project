package com.example.toyproject.data.api

import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Single<RepoSearchResponse>

    @GET("repos/{owner}/{name}")
    fun getRepository(
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String
    ): Single<RepoModel>
}