package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import com.example.android_toy_project_study_2020_mvvm.recyclerview.ItemAdapter
import com.example.android_toy_project_study_2020_mvvm.recyclerview.GitItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityModel(_mainActivity: Activity) {
    val mainActivity = _mainActivity
    init {
        mainActivity.recycler.visibility = INVISIBLE
    }
    fun githubSearch(keyword: String) {
        RetrofitService.getService().requestGithubResponse(keyword = keyword)
            .enqueue(object : Callback<GithubResponseData> {
                override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
                    mainActivity.loading.visibility = INVISIBLE
                    mainActivity.errorTextViewRepository.text = R.string.error.toString() + t.toString()
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
                                mainActivity.errorTextViewRepository.text = R.string.no_response.toString()
                                mainActivity.errorTextViewRepository.visibility = VISIBLE
                                mainActivity.recycler.visibility = INVISIBLE
                            } else {
                                githubResponseData.items.map {
                                    list.add(
                                        GitItem(
                                            it.owner.avatar_url,
                                            it.full_name,
                                            it.language
                                        )
                                    )
                                }
                                val adapter = ItemAdapter(list)
                                mainActivity.recycler.adapter = adapter
                                mainActivity.recycler.visibility = VISIBLE
                            }
                        }
                    } else {
                        mainActivity.errorTextViewRepository.text = R.string.error.toString()
                        mainActivity.errorTextViewRepository.visibility = VISIBLE
                        mainActivity.recycler.visibility = INVISIBLE
                    }
                }

            })
    }
    fun loadingOnOffMainActivity(){
        if (mainActivity.loading.visibility == VISIBLE){
            mainActivity.loading.visibility = INVISIBLE
        } else {
            mainActivity.loading.visibility = VISIBLE
        }
    }
}