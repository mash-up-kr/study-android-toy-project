package com.example.toyproject2020mvvm.model.repository

import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubDetailData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import io.reactivex.disposables.Disposable

interface GitRepositoryInterface {

    fun githubSearch(keyword: String, callback: BaseResponse<GithubResponseData>): Disposable

    fun getDetailRepository(
        userName: String,
        repoName: String,
        callback: BaseResponse<GithubDetailData>
    ): Disposable

}