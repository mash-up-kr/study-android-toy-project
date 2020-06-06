package com.example.android_toy_project_study_2020_mvvm.data

import com.google.gson.annotations.SerializedName

data class GithubDetailUserData (
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int
)