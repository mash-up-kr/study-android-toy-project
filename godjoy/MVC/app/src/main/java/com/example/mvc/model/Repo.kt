package com.example.mvc.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val name: String? = null,
    @SerializedName("full_name") val fullName: String? = null,
    val owner: Owner? = null,
    @SerializedName("stargazers_count") val stargazersCount: Long = 0,
    val  description: String? = null,
    val language: String? = null
)

data class Owner(
    val login: String? = null,
    @SerializedName("avatar_url") val avatarUrl : String? = null
)