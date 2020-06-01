package com.example.android_toy_project_study_2020_mvvm.api

import android.util.Log
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel (){
    fun githubSearch(keyword: String){
    RetrofitService.getService().requestGithubResponse(keyword = keyword).enqueue(object :
        Callback<GithubResponseData> {
        override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
            Log.e("!","onFailure 발생")
        }

        override fun onResponse(call: Call<GithubResponseData>, response: Response<GithubResponseData>) {
            if (response.isSuccessful) {
                val GithubResponseData = response.body()
                if (GithubResponseData != null) {
                    Log.e("!!", GithubResponseData.total_count.toString())
                }
            }
        }

    })}
}