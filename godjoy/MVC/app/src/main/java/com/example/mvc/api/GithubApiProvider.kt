package com.example.mvc.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubApiProvider {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getGithubService(): GithubApi = retrofit.create(GithubApi::class.java)

    }
}