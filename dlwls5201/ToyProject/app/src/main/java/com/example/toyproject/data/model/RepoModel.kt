package com.example.toyproject.data.model

import android.content.Context
import android.text.TextUtils
import com.example.toyproject.R
import com.example.toyproject.ui.model.RepoItem
import com.example.toyproject.utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: OwnerModel,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("stargazers_count")
    val stars: Int
) {
    data class OwnerModel(
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    )
}

fun RepoModel.mapToPresentation(context: Context) = RepoItem(
    title = fullName,
    repoName = name,
    owner = RepoItem.OwnerItem(
        ownerName = owner.login,
        ownerUrl = owner.avatarUrl
    ),

    description = if (TextUtils.isEmpty(description))
        context.resources.getString(R.string.no_description_provided)
    else
        description,

    language = if (TextUtils.isEmpty(language))
        context.resources.getString(R.string.no_language_specified)
    else
        language,

    updatedAt = try {
        DateUtils.dateFormatToShow.format(updatedAt)
    } catch (e: IllegalArgumentException) {
        context.resources.getString(R.string.unknown)
    },

    stars = context.resources.getQuantityString(R.plurals.star, stars, stars)
)
