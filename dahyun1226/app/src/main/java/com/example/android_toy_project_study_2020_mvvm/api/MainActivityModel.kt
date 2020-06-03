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

class MainActivityModel (MainActivity: Activity){
    val MainActivity: Activity=MainActivity
    fun githubSearch(keyword: String){
    RetrofitService.getService().requestGithubResponse(keyword = keyword).enqueue(object :
        Callback<GithubResponseData> {
        override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
            MainActivity.loading.visibility=INVISIBLE
            MainActivity.textView3.text="에러발생 : "+t.toString()
            MainActivity.textView3.visibility= VISIBLE
            MainActivity.recycler.visibility= INVISIBLE
        }

        override fun onResponse(call: Call<GithubResponseData>, response: Response<GithubResponseData>) {
            if (response.isSuccessful) {
                MainActivity.loading.visibility=INVISIBLE
                MainActivity.textView3.visibility= INVISIBLE
                val GithubResponseData = response.body()
                MainActivity.loading.visibility=INVISIBLE
                val list=ArrayList<gitItem>()
                if (GithubResponseData != null) {
                    if(GithubResponseData.items.isEmpty()){
                        MainActivity.textView3.text="결과 없음!!!"
                        MainActivity.textView3.visibility= VISIBLE
                        MainActivity.recycler.visibility= INVISIBLE
                    }
                   else{
                    for( i in GithubResponseData.items.indices)
                        list.add(gitItem(GithubResponseData.items[i].owner.avatar_url,GithubResponseData.items[i].full_name,GithubResponseData.items[i].language))

                    val adapter = ItemAdapter(list)
                    MainActivity.recycler.adapter = adapter
                    MainActivity.recycler.visibility= VISIBLE
                    }
                }
            }
        }

    })}
}