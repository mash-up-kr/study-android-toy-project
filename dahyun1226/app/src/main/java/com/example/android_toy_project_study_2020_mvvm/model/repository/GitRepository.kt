package com.example.android_toy_project_study_2020_mvvm.model.repository

import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GitRepository {
    lateinit var requestGithubResponseData: Call<GithubResponseData>
    lateinit var requestGithubDetailRepoData: Call<GithubDetailRepoData>
    lateinit var requestGithubDetailUserData: Call<GithubDetailUserData>

    fun githubSearch(keyword: String, callback: BaseResponse<GithubResponseData>) {
        callback.onLoading()
        requestGithubResponseData =
            RetrofitService.getService().requestGithubResponse(keyword = keyword)
        requestGithubResponseData!!.enqueue(object : Callback<GithubResponseData> {
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

    fun getDetailRepository(
        userName: String,
        repoName: String,
        callback: BaseResponse<GithubDetailRepoData>
    ) {
        callback.onLoading()
        requestGithubDetailRepoData =
            RetrofitService.getService().requestGetRepository(userName, repoName)
        requestGithubDetailRepoData!!.enqueue(object : Callback<GithubDetailRepoData> {
            override fun onFailure(call: Call<GithubDetailRepoData>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(
                call: Call<GithubDetailRepoData>,
                response: Response<GithubDetailRepoData>
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

    fun getSingleUser(userName: String, callback: BaseResponse<GithubDetailUserData>) {
        callback.onLoading()
        requestGithubDetailUserData = RetrofitService.getService().requestSingleUser(userName)
        requestGithubDetailUserData!!.enqueue(object : Callback<GithubDetailUserData> {
            override fun onFailure(call: Call<GithubDetailUserData>, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(
                call: Call<GithubDetailUserData>,
                response: Response<GithubDetailUserData>
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