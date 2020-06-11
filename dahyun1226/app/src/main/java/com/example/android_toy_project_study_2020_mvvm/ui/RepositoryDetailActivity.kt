package com.example.android_toy_project_study_2020_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    private var requestUser: Call<GithubDetailUserData>? = null
    private var requestRepo: Call<GithubDetailRepoData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repository_detail)
        val fullName = intent.getStringExtra(EXTRA_FULL_NAME)
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        bt_activity_repository_detail_BackButton.setOnClickListener {
            finish()
        }
        loadingVisible()
        errorInvisible()
        layoutInvisible()
        getRepository(userName, repoName)
    }

    fun getRepository(userName: String, repoName: String) {
        requestRepo = RetrofitService.getService().requestGetRepository(userName, repoName)
        requestRepo!!.enqueue(object : Callback<GithubDetailRepoData> {
            override fun onFailure(call: Call<GithubDetailRepoData>, t: Throwable) {
                loadingInvisible()
                errorVisible(t.toString())
            }

            override fun onResponse(
                call: Call<GithubDetailRepoData>,
                response: Response<GithubDetailRepoData>
            ) {
                val repoData = response.body()
                if (repoData != null) {
                    Glide.with(this@RepositoryDetailActivity).load(repoData.owner.avatarUrl)
                        .into(iv_activity_repository_detail_GitAvatarImage)
                    tv_activity_repository_detail_FullName.text = repoData.fullName
                    tv_activity_repository_detail_Stars.text =
                        repoData.stargazersCount.toString() + getString(R.string.stars)
                    tv_activity_repository_detail_Description.text =
                        repoData.description
                    if (repoData.language == null) {
                        tv_activity_repository_detail_Language.text =
                            getString(R.string.no_language)
                    } else {
                        tv_activity_repository_detail_Language.text = repoData.language
                    }
                }
                getSingleUser(userName)
            }
        })
    }

    fun getSingleUser(userName: String) {
        requestUser = RetrofitService.getService().requestSingleUser(userName)
        requestUser!!.enqueue(object : Callback<GithubDetailUserData> {
            override fun onFailure(call: Call<GithubDetailUserData>, t: Throwable) {
                loadingInvisible()
                errorVisible(t.toString())
            }

            override fun onResponse(
                call: Call<GithubDetailUserData>,
                response: Response<GithubDetailUserData>
            ) {
                val userData = response.body()
                if (userData != null) {
                    tv_activity_repository_detail_followersAndFollowing.text =
                        getString(R.string.followers) +
                                " : " + userData.followers.toString() +
                                " , " + getString(R.string.followers) +
                                " : " + userData.following.toString()
                }
                loadingInvisible()
                layoutVisible()
            }
        })
    }

    fun loadingVisible() {
        tv_activity_repository_detail_Loading.visibility = View.VISIBLE
    }

    fun loadingInvisible() {
        tv_activity_repository_detail_Loading.visibility = View.INVISIBLE
    }

    fun errorVisible(errorContent: String) {
        tv_activity_repository_detail_ErrorTextViewRepository.text =
            getString(R.string.error) + errorContent
        tv_activity_repository_detail_ErrorTextViewRepository.visibility = View.VISIBLE
    }

    fun errorInvisible() {
        tv_activity_repository_detail_ErrorTextViewRepository.visibility = View.INVISIBLE
    }

    fun layoutVisible() {
        ll_activity_repository_detail_Layout.visibility = View.VISIBLE
    }

    fun layoutInvisible() {
        ll_activity_repository_detail_Layout.visibility = View.INVISIBLE
    }

    override fun onStop() {
        requestUser?.cancel()
        requestRepo?.cancel()
        super.onStop()
    }
}
