package com.example.mvc.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    val items: ArrayList<Repo>
)