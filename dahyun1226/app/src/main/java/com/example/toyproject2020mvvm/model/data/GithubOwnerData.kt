package com.example.toyproject2020mvvm.model.data

import com.google.gson.annotations.SerializedName

data class GithubOwnerData(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

