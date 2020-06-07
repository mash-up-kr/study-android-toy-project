package com.example.mvc.api

import com.example.mvc.model.Repo
import com.example.mvc.model.SearchResponse
import com.example.mvc.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Call<SearchResponse>

    @GET("repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") ownerLogin: String,
        @Path("repo") repoName: String): Call<Repo>

    @GET("/users/{username}")
    fun getUser( @Path("username") userName: String ): Call<User>
}