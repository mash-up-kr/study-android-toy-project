package com.example.toyproject2020mvvm.model.data

import com.google.gson.annotations.SerializedName

data class GithubDetailRepoData(
    @SerializedName("owner")
    val owner: GithubOwnerData,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
)