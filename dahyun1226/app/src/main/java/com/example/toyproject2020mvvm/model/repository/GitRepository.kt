package com.example.toyproject2020mvvm.model.repository

import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.data.GithubDetailData
import com.example.toyproject2020mvvm.model.data.GithubDetailRepoData
import com.example.toyproject2020mvvm.model.data.GithubDetailUserData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

object GitRepository {
    lateinit var requestGithubResponseData: Single<GithubResponseData>
    lateinit var requestGithubDetailRepoData: Single<GithubDetailRepoData>
    lateinit var requestGithubDetailUserData: Single<GithubDetailUserData>

    fun githubSearch(keyword: String, callback: BaseResponse<GithubResponseData>): Disposable {
        callback.onLoading()
        requestGithubResponseData =
            RetrofitService.getService().requestGithubResponse(keyword = keyword)
        return requestGithubResponseData
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
    ): Disposable {
        callback.onLoading()
        requestGithubDetailRepoData =
            RetrofitService.getService().requestGetRepository(userName, repoName)
        requestGithubDetailUserData = RetrofitService.getService().requestSingleUser(userName)
        return Single.zip(
            requestGithubDetailRepoData,
            requestGithubDetailUserData,
            BiFunction { a: GithubDetailRepoData, b: GithubDetailUserData ->
                (GithubDetailData(a, b))
            })
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