package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import com.example.android_toy_project_study_2020_mvvm.recyclerview.ItemAdapter
import com.example.android_toy_project_study_2020_mvvm.recyclerview.GitItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityModel(_mainActivity: Activity) {
    val mainActivity = _mainActivity
    fun githubSearch(keyword: String) {
        RetrofitService.getService().requestGithubResponse(keyword = keyword)
            .enqueue(object : Callback<GithubResponseData> {
                override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
                    mainActivity.loading.visibility = INVISIBLE
                    mainActivity.errorTextViewRepository.text = "에러발생 : " + t.toString()
                    mainActivity.errorTextViewRepository.visibility = VISIBLE
                    mainActivity.recycler.visibility = INVISIBLE
                }

                override fun onResponse(
                    call: Call<GithubResponseData>,
                    response: Response<GithubResponseData>
                ) {
                    if (response.isSuccessful) {
                        mainActivity.loading.visibility = INVISIBLE
                        mainActivity.errorTextViewRepository.visibility = INVISIBLE
                        val githubResponseData = response.body()
                        mainActivity.loading.visibility = INVISIBLE
                        val list = ArrayList<GitItem>()
                        if (githubResponseData != null) {
                            if (githubResponseData.items.isEmpty()) {
                                mainActivity.errorTextViewRepository.text = "결과 없음!!!"
                                mainActivity.errorTextViewRepository.visibility = VISIBLE
                                mainActivity.recycler.visibility = INVISIBLE
                            } else {
                                for (i in githubResponseData.items.indices) {
                                    list.add(
                                        GitItem(
                                            githubResponseData.items[i].owner.avatar_url,
                                            githubResponseData.items[i].full_name,
                                            githubResponseData.items[i].language
                                        )
                                    )
                                }
                                val adapter = ItemAdapter(list)
                                mainActivity.recycler.adapter = adapter
                                mainActivity.recycler.visibility = VISIBLE
                            }
                        }
                    } else {

                    }
                }

            })
    }
}