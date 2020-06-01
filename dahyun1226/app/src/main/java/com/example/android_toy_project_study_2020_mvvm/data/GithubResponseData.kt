package com.example.android_toy_project_study_2020_mvvm.data

data class GithubResponseData (
    val total_count : Int,
    val items: List<GithubRepoData>
)