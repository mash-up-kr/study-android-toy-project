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

    val errorTextId = ObservableField(R.string.error)

    val descriptionText = ObservableField("")

    val languageNotExist = ObservableField(R.string.no_language)

    val languageText = ObservableField("")

    val imageUrl = ObservableField("")

    val followersNum = ObservableField("")

    val followingsNum = ObservableField("")

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
                    if (data.githubDetailRepoData.language != null) {
                        languageText.set(
                            data.githubDetailRepoData.language
                        )
                    }
                    followersNum.set(data.githubDetailUserData.followers.toString())
                    followingsNum.set(data.githubDetailUserData.following.toString())
                }

                override fun onError(throwable: Throwable) {
                    hideLoading()
                    showError(R.string.error)
                    hideLayout()
                }

                override fun onLoading() {
                    showLoading()
                    hideError()
                    hideLayout()
                }

                override fun onLoaded() {
                    hideLoading()
                    hideError()
                    showLayout()
                }
            }).also {
            compositeDisposable.add(it)
        }
    }

    fun showLayout() {
        layoutVisible.set(true)
    }

    fun hideLayout() {
        layoutVisible.set(false)
    }

    fun showLoading() {
        loadingVisible.set(true)
    }

    fun hideLoading() {
        loadingVisible.set(false)
    }

    fun showError(errorContent: Int) {
        errorTextId.set(errorContent)
        errorTextVisible.set(true)
    }

    fun hideError() {
        errorTextVisible.set(false)
    }

}