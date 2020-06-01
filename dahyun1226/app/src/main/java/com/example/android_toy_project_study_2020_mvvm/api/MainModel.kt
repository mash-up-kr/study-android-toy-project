package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import com.example.android_toy_project_study_2020_mvvm.recyclerview.ItemAdapter
import com.example.android_toy_project_study_2020_mvvm.recyclerview.gitItem
import com.example.android_toy_project_study_2020_mvvm.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel (MainActivity: Activity){
    val MainActivity: Activity=MainActivity
    fun githubSearch(keyword: String){
    RetrofitService.getService().requestGithubResponse(keyword = keyword).enqueue(object :
        Callback<GithubResponseData> {
        override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
            Log.e("!","onFailure 발생")
        }

        override fun onResponse(call: Call<GithubResponseData>, response: Response<GithubResponseData>) {
            if (response.isSuccessful) {
                val GithubResponseData = response.body()
                MainActivity.loading.visibility=INVISIBLE
                val list=ArrayList<gitItem>()
                if (GithubResponseData != null) {
                    for( i in 0..GithubResponseData.items.size-1)
                        list.add(gitItem(GithubResponseData.items[i].owner.avatar_url,GithubResponseData.items[i].full_name,GithubResponseData.items[i].language))
                }
                val adapter = ItemAdapter(list)
                MainActivity.recycler.adapter = adapter
                MainActivity.recycler.visibility= VISIBLE
            }
        }

    })}
}