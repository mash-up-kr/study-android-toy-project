package com.example.toyproject.data.model

import android.content.Context
import com.example.toyproject.R
import com.example.toyproject.ui.model.UserItem
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("avatar_url")
    val profileImgUrl: String
)

fun UserModel.mapToPresentation(context: Context) = UserItem(
    followers = followers.let {
        if (it > 100) context.getString(R.string.max_follow_number) else it.toString()
    },
    following = following.let {
        if (it > 100) context.getString(R.string.max_follow_number) else it.toString()
    }
)