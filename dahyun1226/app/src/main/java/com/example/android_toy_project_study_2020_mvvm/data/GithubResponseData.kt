package com.example.android_toy_project_study_2020_mvvm.data

import com.google.gson.annotations.SerializedName

data class GithubResponseData (
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<GithubRepoData>
)