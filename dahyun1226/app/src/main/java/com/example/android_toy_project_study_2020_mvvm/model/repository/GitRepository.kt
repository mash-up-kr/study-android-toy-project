package com.example.android_toy_project_study_2020_mvvm.model.repository

import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GitRepository {
    lateinit var requestGithubResponseData: Single<GithubResponseData>
    lateinit var requestGithubDetailRepoData: Single<GithubDetailRepoData>
    lateinit var requestGithubDetailUserData: Single<GithubDetailUserData>

    fun githubSearch(keyword: String, callback: BaseResponse<GithubResponseData>) {
        callback.onLoading()
        requestGithubResponseData =
            RetrofitService.getService().requestGithubResponse(keyword = keyword)
        requestGithubResponseData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onLoaded()
                callback.onSuccess(it)
            }, {
                callback.onError(it)
            })
    }

    fun getDetailRepository(
        userName: String,
        repoName: String,
        callback: BaseResponse<GithubDetailRepoData>
    ) {
        callback.onLoading()
        requestGithubDetailRepoData =
            RetrofitService.getService().requestGetRepository(userName, repoName)
        requestGithubDetailRepoData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onLoaded()
                callback.onSuccess(it)
            }, {
                callback.onError(it)
            })
    }

    fun getSingleUser(userName: String, callback: BaseResponse<GithubDetailUserData>) {
        callback.onLoading()
        requestGithubDetailUserData = RetrofitService.getService().requestSingleUser(userName)
        requestGithubDetailUserData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onLoaded()
                callback.onSuccess(it)
            }, {
                callback.onError(it)
            })
    }
}