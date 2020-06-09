package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailUserData
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailActivityController(_activity: Activity) {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    val activity = _activity

    init {
        activity.tv_activity_repository_detail_Loading.visibility = VISIBLE
        activity.tv_activity_repository_detail_ErrorTextViewRepository.visibility = INVISIBLE
        activity.ll_activity_repository_detail_Layout.visibility = INVISIBLE
    }

    fun getRepository(userName: String, repoName: String) {
        RetrofitService.getService().requestGetRepository(userName, repoName)
            .enqueue(object : Callback<GithubDetailRepoData> {
                override fun onFailure(call: Call<GithubDetailRepoData>, t: Throwable) {
                    activity.tv_activity_repository_detail_Loading.visibility = INVISIBLE
                    activity.tv_activity_repository_detail_ErrorTextViewRepository.text =
                        activity.getString(R.string.error) + t.toString()
                    activity.tv_activity_repository_detail_ErrorTextViewRepository.visibility =
                        VISIBLE
                }

                override fun onResponse(
                    call: Call<GithubDetailRepoData>,
                    response: Response<GithubDetailRepoData>
                ) {
                    val repoData = response.body()
                    if (repoData != null) {
                        Glide.with(activity).load(repoData.owner.avatarUrl)
                            .into(activity.iv_activity_repository_detail_GitAvatarImage)
                        activity.tv_activity_repository_detail_FullName.text = repoData.fullName
                        activity.tv_activity_repository_detail_Stars.text =
                            repoData.stargazersCount.toString() + activity.getString(R.string.stars)
                        activity.tv_activity_repository_detail_Description.text =
                            repoData.description
                        if (repoData.language == null) {
                            activity.tv_activity_repository_detail_Language.text =
                                activity.getString(R.string.no_language)
                        } else {
                            activity.tv_activity_repository_detail_Language.text = repoData.language
                        }
                    }
                    getSingleUser(userName)
                }
            })
    }

    fun getSingleUser(userName: String) {
        RetrofitService.getService().requestSingleUser(userName)
            .enqueue(object : Callback<GithubDetailUserData> {
                override fun onFailure(call: Call<GithubDetailUserData>, t: Throwable) {
                    activity.tv_activity_repository_detail_Loading.visibility = INVISIBLE
                    activity.tv_activity_repository_detail_ErrorTextViewRepository.text =
                        activity.getString(R.string.error) + t.toString()
                    activity.tv_activity_repository_detail_ErrorTextViewRepository.visibility =
                        VISIBLE
                }

                override fun onResponse(
                    call: Call<GithubDetailUserData>,
                    response: Response<GithubDetailUserData>
                ) {
                    val userData = response.body()
                    if (userData != null) {
                        activity.tv_activity_repository_detail_followersAndFollowing.text =
                            activity.getString(R.string.followers) +
                                    " : " + userData.followers.toString() +
                                    " , " + activity.getString(R.string.followers) +
                                    " : " + userData.following.toString()
                    }
                    activity.tv_activity_repository_detail_Loading.visibility = INVISIBLE
                    activity.ll_activity_repository_detail_Layout.visibility = VISIBLE
                }
            })
    }
}