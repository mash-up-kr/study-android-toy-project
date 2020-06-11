package com.example.android_toy_project_study_2020_mvvm.model.repository

import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GitRepository {
    lateinit var request: Call<GithubResponseData>
    fun githubSearch(keyword: String, callback: BaseResponse<GithubResponseData>) {
        callback.onLoading()
        request = RetrofitService.getService().requestGithubResponse(keyword = keyword)
        request!!.enqueue(object : Callback<GithubResponseData> {
            override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(
                call: Call<GithubResponseData>,
                response: Response<GithubResponseData>
            ) {
                if (response.isSuccessful) {
                    callback.onLoaded()
                    response.body()?.let { callback.onSuccess(it) }
                } else {
                    callback.onFail(R.string.no_response)
                }
            }
        })
    }
}