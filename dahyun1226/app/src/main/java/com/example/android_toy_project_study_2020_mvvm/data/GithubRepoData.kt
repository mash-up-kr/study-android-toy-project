package com.example.android_toy_project_study_2020_mvvm.data

import com.google.gson.annotations.SerializedName

data class GithubRepoData(
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: GithubOwnerData,
    @SerializedName("language")
    val language: String?
)

