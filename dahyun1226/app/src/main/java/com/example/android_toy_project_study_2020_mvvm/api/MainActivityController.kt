package com.example.android_toy_project_study_2020_mvvm.api

import android.app.Activity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.R.*
import com.example.android_toy_project_study_2020_mvvm.data.GithubRepoData
import com.example.android_toy_project_study_2020_mvvm.data.GithubResponseData
import com.example.android_toy_project_study_2020_mvvm.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityController(_mainActivity: Activity) {
    val mainActivity = _mainActivity
    init {
        mainActivity.rc_activity_main_Recycler.visibility = INVISIBLE
    }
    fun githubSearch(keyword: String) {
        RetrofitService.getService().requestGithubResponse(keyword = keyword)
            .enqueue(object : Callback<GithubResponseData> {
                override fun onFailure(call: Call<GithubResponseData>, t: Throwable) {
                    mainActivity.tv_activity_main_Loading.visibility = INVISIBLE
                    mainActivity.tv_activity_main_ErrorTextViewRepository.text = mainActivity.getString(R.string.error) + t.toString()
                    mainActivity.tv_activity_main_ErrorTextViewRepository.visibility = VISIBLE
                    mainActivity.rc_activity_main_Recycler.visibility = INVISIBLE
                }

                override fun onResponse(
                    call: Call<GithubResponseData>,
                    response: Response<GithubResponseData>
                ) {
                    if (response.isSuccessful) {
                        mainActivity.tv_activity_main_Loading.visibility = INVISIBLE
                        mainActivity.tv_activity_main_ErrorTextViewRepository.visibility = INVISIBLE
                        val githubResponseData = response.body()
                        mainActivity.tv_activity_main_Loading.visibility = INVISIBLE
                        val list = ArrayList<GithubRepoData>()
                        if (githubResponseData != null) {
                            if (githubResponseData.items.isEmpty()) {
                                mainActivity.tv_activity_main_ErrorTextViewRepository.text = mainActivity.getString(string.no_response)
                                mainActivity.tv_activity_main_ErrorTextViewRepository.visibility = VISIBLE
                                mainActivity.rc_activity_main_Recycler.visibility = INVISIBLE
                            } else {
                                githubResponseData.items.map {
                                    list.add(it)
                                }
                                val adapter = ItemAdapter(list)
                                mainActivity.rc_activity_main_Recycler.adapter = adapter
                                mainActivity.rc_activity_main_Recycler.visibility = VISIBLE
                            }
                        }
                    } else {
                        mainActivity.tv_activity_main_ErrorTextViewRepository.text = mainActivity.getString(R.string.error)
                        mainActivity.tv_activity_main_ErrorTextViewRepository.visibility = VISIBLE
                        mainActivity.rc_activity_main_Recycler.visibility = INVISIBLE
                    }
                }

            })
    }
    fun loadingOnOffMainActivity(){
        if (mainActivity.tv_activity_main_Loading.visibility == VISIBLE){
            mainActivity.tv_activity_main_Loading.visibility = INVISIBLE
        } else {
            mainActivity.tv_activity_main_Loading.visibility = VISIBLE
        }
    }
}