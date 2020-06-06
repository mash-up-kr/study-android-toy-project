package com.example.android_toy_project_study_2020_mvvm.data

import com.google.gson.annotations.SerializedName

data class GithubOwnerData (
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

