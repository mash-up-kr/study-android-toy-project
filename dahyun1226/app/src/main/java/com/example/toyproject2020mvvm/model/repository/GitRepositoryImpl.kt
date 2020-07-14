package com.example.toyproject2020mvvm.model.repository

import android.util.Log
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.api.RetrofitServiceInterface
import com.example.toyproject2020mvvm.model.data.GithubDetailData
import com.example.toyproject2020mvvm.model.data.GithubDetailRepoData
import com.example.toyproject2020mvvm.model.data.GithubDetailUserData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class GitRepositoryImpl(
    private val api: RetrofitServiceInterface
) : GitRepositoryInterface {

    override fun githubSearch(
        keyword: String,
        callback: BaseResponse<GithubResponseData>
    ): Disposable {
        return api.requestGithubResponse(keyword)
            .doOnSubscribe { callback.onLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onLoaded()
                callback.onSuccess(it)
            }, {
                callback.onError(it)
            })
    }

    override fun getDetailRepository(
        userName: String,
        repoName: String,
        callback: BaseResponse<GithubDetailData>
    ): Disposable {
        return Single.zip(
            api.requestGetRepository(userName, repoName),
            api.requestSingleUser(userName),
            BiFunction { a: GithubDetailRepoData, b: GithubDetailUserData ->
                (GithubDetailData(a, b))
            })
            .doOnSubscribe { callback.onLoading() }
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