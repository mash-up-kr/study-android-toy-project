package com.example.toyproject.repository

import com.example.toyproject.data.api.RepoApi
import com.example.toyproject.data.api.UserApi
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoRepositoryImpl(
    private val repoApi: RepoApi,
    private val userApi: UserApi
) : RepoRepository {

    override fun searchRepositories(query: String, callback: BaseResponse<RepoSearchResponse>) {
        callback.onLoading()

        repoApi.searchRepository(query)
            .enqueue(object : Callback<RepoSearchResponse> {
                override fun onResponse(
                    call: Call<RepoSearchResponse>,
                    response: Response<RepoSearchResponse>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && null != body) {
                        callback.onSuccess(body)
                    } else {
                        callback.onFail(response.message())
                    }

                    callback.onLoaded()
                }

                override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                    callback.onError(t)
                    callback.onLoaded()
                }
            })
    }

    override fun getDetailRepository(
        user: String, repo: String, callback: BaseResponse<RepoDetailModel>
    ) {
        callback.onLoading()

        repoApi.getRepository(user, repo)
            .enqueue(object : Callback<RepoModel> {
                override fun onResponse(
                    call: Call<RepoModel>,
                    response: Response<RepoModel>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && null != body) {
                        val repoModel = body

                        userApi.getUser(user)
                            .enqueue(object : Callback<UserModel> {
                                override fun onResponse(
                                    call: Call<UserModel>,
                                    response: Response<UserModel>
                                ) {
                                    val body = response.body()
                                    if (response.isSuccessful && null != body) {
                                        val userModel = body

                                        callback.onSuccess(
                                            RepoDetailModel(
                                                title = repoModel.fullName,
                                                repoName = repoModel.name,
                                                ownerName = userModel.name,
                                                ownerUrl = userModel.profileImgUrl,
                                                followers = userModel.followers,
                                                following = userModel.following,
                                                description = repoModel.description,
                                                language = repoModel.language,
                                                updatedAt = repoModel.updatedAt,
                                                stars = repoModel.stars
                                            )
                                        )
                                    } else {
                                        callback.onFail(response.message())
                                    }

                                    callback.onLoaded()
                                }

                                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                                    callback.onError(t)
                                    callback.onLoaded()
                                }

                            })

                    } else {
                        callback.onFail(response.message())
                        callback.onLoaded()
                    }
                }

                override fun onFailure(call: Call<RepoModel>, t: Throwable) {
                    callback.onError(t)
                    callback.onLoaded()
                }
            })
    }
}