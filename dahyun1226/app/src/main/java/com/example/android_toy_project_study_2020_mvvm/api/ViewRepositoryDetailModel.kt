package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailUserData
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import kotlinx.android.synthetic.main.activity_view_repository_detail.loading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewRepositoryDetailModel (_activity: Activity){
    val activity = _activity
    init {
        activity.loading.visibility = VISIBLE
        activity.errorTextViewRepository.visibility = INVISIBLE
        activity.layout.visibility = INVISIBLE
    }
    fun getRepository(userName: String, repoName:String){
        RetrofitService.getService().requestGetRepository(userName,repoName).enqueue(object: Callback<GithubDetailRepoData>{
            override fun onFailure(call: Call<GithubDetailRepoData>, t: Throwable) {
                activity.loading.visibility = INVISIBLE
                activity.errorTextViewRepository.text = "에러발생! : " + t.toString()
                activity.errorTextViewRepository.visibility = VISIBLE
            }
            override fun onResponse(call: Call<GithubDetailRepoData>, response: Response<GithubDetailRepoData>) {
                val repoData=response.body()
                if (repoData != null) {
                    Glide.with(activity).load(repoData.owner.avatar_url).into(activity.gitAvatarImage)
                    activity.fullName.text = repoData.full_name
                    activity.stars.text = repoData.stargazers_count.toString() + " stars"
                    activity.description.text = repoData.description
                    if (repoData.language==null){
                        activity.language.text = "No language specified"
                    } else {
                        activity.language.text = repoData.language
                    }
                }
                getSingleUser(userName)
            }
        })
    }
    fun getSingleUser(userName: String){
        RetrofitService.getService().requestSingleUser(userName).enqueue(object: Callback<GithubDetailUserData>{
            override fun onFailure(call: Call<GithubDetailUserData>, t: Throwable) {
                activity.loading.visibility = INVISIBLE
                activity.errorTextViewRepository.text = "에러발생! : " + t.toString()
                activity.errorTextViewRepository.visibility = VISIBLE
            }
            override fun onResponse(call: Call<GithubDetailUserData>, response: Response<GithubDetailUserData>) {
                val userData = response.body()
                if (userData != null) {
                    activity.followersAndFollowing.text = "followers : " + userData.followers.toString() + ", following : " + userData.following.toString()
                }
                activity.loading.visibility = INVISIBLE
                activity.layout.visibility = VISIBLE
            }
        })
    }
}