package com.example.toyproject2020mvvm.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubDetailData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import io.reactivex.disposables.CompositeDisposable

class RepositoryDetailViewModel(
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val loadingVisible = ObservableField(false)

    val layoutVisible = ObservableField(false)

    val errorTextVisible = ObservableField(false)

    val fullNameText = ObservableField("")

    val starText = ObservableField("")

    val starTextId = ObservableField(0)

    val errorText = ObservableField("")

    val errorTextId = ObservableField(0)

    val descriptionText = ObservableField("")

    val languageText = ObservableField("")

    val languageTextId = ObservableField(0)

    val imageUrl = ObservableField("")

    val followersAndFollowingsText = ObservableField("")

    val followers = ObservableField("")

    val followings = ObservableField("")

    fun getDetailRepository(fullName: String) {
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        repository.getDetailRepository(
            userName,
            repoName,
            object : BaseResponse<GithubDetailData> {
                override fun onSuccess(data: GithubDetailData) {
                    imageUrl.set(data.githubDetailRepoData.owner.avatarUrl)
                    fullNameText.set(data.githubDetailRepoData.fullName)
                    starText.set(
                        data.githubDetailRepoData.stargazersCount.toString()
                    )
                    descriptionText.set(
                        data.githubDetailRepoData.description
                    )
                    if (data.githubDetailRepoData.language == null) {
                        languageTextId.set(R.string.no_language)
                    } else {
                        languageText.set(
                            data.githubDetailRepoData.language
                        )
                    }
                    followers.set(data.githubDetailUserData.followers.toString())
                    followings.set(data.githubDetailUserData.following.toString())
                }

                override fun onError(throwable: Throwable) {
                    loadingInvisible()
                    errorVisible(R.string.error)
                    layoutInvisible()
                }

                override fun onLoading() {
                    loadingVisible()
                    errorInvisible()
                    layoutInvisible()
                }

                override fun onLoaded() {
                    loadingInvisible()
                    errorInvisible()
                    layoutVisible()
                }
            }).also {
            compositeDisposable.add(it)
        }
    }

    fun layoutVisible() {
        layoutVisible.set(true)
    }

    fun layoutInvisible() {
        layoutVisible.set(false)
    }

    fun loadingVisible() {
        loadingVisible.set(true)
    }

    fun loadingInvisible() {
        loadingVisible.set(false)
    }

    fun errorVisible(errorContent: Int) {
        errorTextId.set(errorContent)
        errorTextVisible.set(true)
    }

    fun errorInvisible() {
        errorTextVisible.set(false)
    }
}

@BindingAdapter("bind_image")
fun setImage(view: ImageView, url: String) {
    Glide.with(view).load(url)
        .into(view)
}