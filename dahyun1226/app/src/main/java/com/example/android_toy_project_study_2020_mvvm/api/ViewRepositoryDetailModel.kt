package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewRepositoryDetailModel (Activity: Activity){
    val activity = Activity
    fun getRepository(userName: String, repoName:String){
        RetrofitService.getService().requestGetRepository(userName,repoName).enqueue(object: Callback<GithubDetailRepoData>{
            override fun onFailure(call: Call<GithubDetailRepoData>, t: Throwable) {
            }
            override fun onResponse(call: Call<GithubDetailRepoData>, response: Response<GithubDetailRepoData>) {
                val repoData=response.body()
                if (repoData != null) {
                    Glide.with(activity).load(repoData.owner.avatar_url).into(activity.imageView2)
                    activity.textView4.text = repoData.full_name
                    activity.textView5.text = repoData.stargazers_count.toString() + " stars"
                    activity.textView6.text = repoData.description
                    if (repoData.language==null){
                        activity.textView7.text = "No language specified"
                    } else {
                        activity.textView7.text = repoData.language
                    }
                }
                getSingleUser(userName)
            }
        })
    }
    fun getSingleUser(userName: String){
        RetrofitService.getService().requestSingleUser(userName).enqueue(object: Callback<GithubDetailUserData>{
                override fun onFailure(call: Call<GithubDetailUserData>, t: Throwable) {
                }
                override fun onResponse(call: Call<GithubDetailUserData>, response: Response<GithubDetailUserData>) {
                    val userData = response.body()
                    if (userData != null) {
                        activity.followersAndFollowing.text = "followers : " + userData.followers.toString() + ", following : " + userData.following.toString()
                    }
                    activity.loading.visibility = INVISIBLE
                    activity.layout.visibility = VISIBLE
                }
            }
        )
    }
}