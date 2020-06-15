package com.example.toyproject.repository

import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.RepoSearchResponse
import io.reactivex.disposables.Disposable

interface RepoRepository {

    fun searchRepositories(query: String, callback: BaseResponse<RepoSearchResponse>): Disposable

    fun getDetailRepository(
        user: String,
        repo: String,
        callback: BaseResponse<RepoDetailModel>
    ): Disposable
}