package com.example.android_toy_project_study_2020_mvvm.data

data class GithubRepoData (
    val name: String,
    val full_name: String,
    val owner: GithubOwnerData,
    val language: String?
)

