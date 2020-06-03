package com.example.android_toy_project_study_2020_mvvm.data

data class GithubDetailRepoData (
    val owner : GithubOwnerData,
    val full_name : String,
    val language : String?,
    val description :String,
    val stargazers_count : Int
)