package com.example.android_toy_project_study_2020_mvvm.ui

import android.os.BadParcelableException
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubResponseData
import com.example.android_toy_project_study_2020_mvvm.model.repository.GitRepository
import com.example.android_toy_project_study_2020_mvvm.ui.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_activity_main_SearchButton.setOnClickListener {
            if (et_activity_main_Search.text.toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.put_contents), Toast.LENGTH_SHORT).show()
            } else {
                loadingVisible()
                GitRepository.githubSearch(et_activity_main_Search.text.toString(),
                    object : BaseResponse<GithubResponseData> {
                        override fun onSuccess(data: GithubResponseData) {
                            if (data.totalCount == 0) {
                                loadingInvisible()
                                recyclerInvisible()
                                errorVisible(getString(R.string.no_response))
                            } else {
                                recyclerVisible()
                                rc_activity_main_Recycler.adapter =
                                    ItemAdapter(data.items as ArrayList<GithubRepoData>)
                            }
                        }

                        override fun onFail(description: Int) {
                            loadingInvisible()
                            recyclerInvisible()
                            errorVisible(getString(description))
                        }

                        override fun onError(throwable: Throwable) {
                            loadingInvisible()
                            recyclerInvisible()
                            errorVisible(throwable.toString())
                        }

                        override fun onLoading() {
                            recyclerInvisible()
                            loadingVisible()
                            errorInvisible()
                        }

                        override fun onLoaded() {
                            loadingInvisible()
                            recyclerVisible()
                            errorInvisible()
                        }
                    })
            }
        }
        recyclerInvisible()
    }

    fun loadingVisible() {
        tv_activity_main_Loading.visibility = VISIBLE
    }

    fun loadingInvisible() {
        tv_activity_main_Loading.visibility = INVISIBLE
    }

    fun recyclerVisible() {
        rc_activity_main_Recycler.visibility = VISIBLE
    }

    fun recyclerInvisible() {
        rc_activity_main_Recycler.visibility = INVISIBLE
    }

    fun errorVisible(errorContent: String) {
        tv_activity_main_ErrorTextViewRepository.text =
            getString(R.string.error) + errorContent
        tv_activity_main_ErrorTextViewRepository.visibility = VISIBLE
    }

    fun errorInvisible() {
        tv_activity_main_ErrorTextViewRepository.visibility = INVISIBLE
    }

    override fun onStop() {
        GitRepository.requestGithubResponseData.cancel()
        super.onStop()
    }
}

