package com.example.mashuptoyproject.network.model


data class GithubItem(
    var items: List<Item>,
    var total_count : Int
)

data class Item(
    var name: String,
    var owner: Owner,
    var language: String,
    var stargazers_count: Int,
    var description: String
)

data class Owner(
    var login: String,
    var avatar_url: String
)

