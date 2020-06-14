package com.example.toyproject2020mvvm.model.data

import com.google.gson.annotations.SerializedName

data class GithubResponseData(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<GithubRepoData>
)