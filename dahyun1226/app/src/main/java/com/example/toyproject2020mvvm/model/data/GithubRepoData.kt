package com.example.toyproject2020mvvm.model.data

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

