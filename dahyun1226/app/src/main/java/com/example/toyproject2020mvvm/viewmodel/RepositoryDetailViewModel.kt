package com.example.toyproject2020mvvm.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubDetailData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import io.reactivex.disposables.CompositeDisposable

class RepositoryDetailViewModel(
    private val repository: GitRepositoryInterface
) : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val loadingVisible = MutableLiveData<Boolean>(false)

    val layoutVisible = MutableLiveData<Boolean>(false)

    val errorTextVisible = MutableLiveData<Boolean>(false)

    val fullNameText = MutableLiveData<String>("")

    val starText = MutableLiveData<String>("")

    val errorTextId = MutableLiveData<Int>(R.string.error)

    val descriptionText = MutableLiveData<String>("")

    val languageText = MutableLiveData<String>()

    val imageUrl = MutableLiveData<String>("")

    val followersNum = MutableLiveData<String>("")

    val followingsNum = MutableLiveData<String>("")

    fun getDetailRepository(fullName: String) {
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        repository.getDetailRepository(
            userName,
            repoName,
            object : BaseResponse<GithubDetailData> {
                override fun onSuccess(data: GithubDetailData) {
                    imageUrl.postValue(data.githubDetailRepoData.owner.avatarUrl)
                    fullNameText.postValue(data.githubDetailRepoData.fullName)
                    starText.postValue(
                        data.githubDetailRepoData.stargazersCount.toString()
                    )
                    descriptionText.postValue(
                        data.githubDetailRepoData.description
                    )
                    if (data.githubDetailRepoData.language != null) {
                        languageText.postValue(
                            data.githubDetailRepoData.language
                        )
                    }
                    followersNum.postValue(data.githubDetailUserData.followers.toString())
                    followingsNum.postValue(data.githubDetailUserData.following.toString())
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
        layoutVisible.postValue(true)
    }

    fun hideLayout() {
        layoutVisible.postValue(false)
    }

    fun showLoading() {
        loadingVisible.postValue(true)
    }

    fun hideLoading() {
        loadingVisible.postValue(false)
    }

    fun showError(errorContent: Int) {
        errorTextId.postValue(errorContent)
        errorTextVisible.postValue(true)
    }

    fun hideError() {
        errorTextVisible.postValue(false)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}