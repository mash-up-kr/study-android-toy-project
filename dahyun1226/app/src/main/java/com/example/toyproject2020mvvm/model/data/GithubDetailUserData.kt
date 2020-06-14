package com.example.toyproject2020mvvm.model.data

import com.google.gson.annotations.SerializedName

data class GithubDetailUserData(
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int
)