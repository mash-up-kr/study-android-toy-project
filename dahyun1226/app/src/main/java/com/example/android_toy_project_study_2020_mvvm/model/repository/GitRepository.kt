package com.example.android_toy_project_study_2020_mvvm.model.repository

import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GitRepository {
    lateinit var requestGithubResponseData: Observable<GithubResponseData>
    lateinit var requestGithubDetailRepoData: Observable<GithubDetailRepoData>
    lateinit var requestGithubDetailUserData: Observable<GithubDetailUserData>

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
        callback: BaseResponse<GithubDetailData>
    ) {
        callback.onLoading()
        requestGithubDetailRepoData =
            RetrofitService.getService().requestGetRepository(userName, repoName)
        requestGithubDetailUserData = RetrofitService.getService().requestSingleUser(userName)
        val firstRequest = requestGithubDetailRepoData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val secondRequest = requestGithubDetailUserData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        Observable.zip(
            firstRequest,
            secondRequest,
            BiFunction { a: GithubDetailRepoData, b: GithubDetailUserData ->
                (GithubDetailData(a, b))
            }).subscribe({
            callback.onLoaded()
            callback.onSuccess(it)
        }, {
            callback.onError(it)
        })
    }
}