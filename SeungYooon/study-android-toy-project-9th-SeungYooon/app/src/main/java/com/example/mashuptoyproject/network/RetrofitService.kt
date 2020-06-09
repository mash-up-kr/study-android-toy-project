package com.example.mashuptoyproject.network

import com.example.mashuptoyproject.network.model.GithubItem
import com.example.mashuptoyproject.network.model.Item
import com.example.mashuptoyproject.network.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("/search/repositories")
    fun requestData(@Query("q") name: String): Call<GithubItem>

    @GET("/repos/{owner}/{repo}")
    fun requestOwner(@Path("owner") owner: String, @Path("repo") repo: String): Call<Item>

    @GET("/users/{username}")
    fun requestUser(@Path("username") username: String) : Call<User>
}