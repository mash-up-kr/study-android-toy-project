package com.example.android_toy_project_study_2020_mvvm.data

import com.google.gson.annotations.SerializedName

data class GithubDetailRepoData (
    val owner: GithubOwnerData,
    @SerializedName("full_name")
    val fullName: String,
    val language: String?,
    val description:String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
)