package com.example.toyproject.data.api

import com.example.toyproject.data.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{name}")
    fun getUser(@Path("name") userName: String): Call<UserModel>
}