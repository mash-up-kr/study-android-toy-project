package com.example.android_toy_project_study_2020_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.model.BaseResponse
import com.example.android_toy_project_study_2020_mvvm.model.api.RetrofitService
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailRepoData
import com.example.android_toy_project_study_2020_mvvm.model.data.GithubDetailUserData
import com.example.android_toy_project_study_2020_mvvm.model.repository.GitRepository
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    private val compositeDisposable = CompositeDisposable()

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
        GitRepository.getDetailRepository(
            userName,
            repoName,
            object : BaseResponse<GithubDetailData> {
                override fun onSuccess(data: GithubDetailData) {
                    Glide.with(this@RepositoryDetailActivity)
                        .load(data.githubDetailRepoData.owner.avatarUrl)
                        .into(iv_activity_repository_detail_GitAvatarImage)
                    tv_activity_repository_detail_FullName.text = data.githubDetailRepoData.fullName
                    tv_activity_repository_detail_Stars.text =
                        data.githubDetailRepoData.stargazersCount.toString() + getString(R.string.stars)
                    tv_activity_repository_detail_Description.text =
                        data.githubDetailRepoData.description
                    if (data.githubDetailRepoData.language == null) {
                        tv_activity_repository_detail_Language.text =
                            getString(R.string.no_language)
                    } else {
                        tv_activity_repository_detail_Language.text =
                            data.githubDetailRepoData.language
                    }
                    tv_activity_repository_detail_followersAndFollowing.text =
                        getString(R.string.followers) +
                                " : " + data.githubDetailUserData.followers.toString() +
                                " , " + getString(R.string.followers) +
                                " : " + data.githubDetailUserData.following.toString()
                }

                override fun onFail(description: Int) {
                    loadingInvisible()
                    errorVisible(getString(description))
                    layoutInvisible()
                }

                override fun onError(throwable: Throwable) {
                    loadingInvisible()
                    errorVisible(throwable.toString())
                    layoutInvisible()
                }

                override fun onLoading() {
                    loadingVisible()
                    errorInvisible()
                    layoutInvisible()
                }

                override fun onLoaded() {
                    loadingInvisible()
                    errorInvisible()
                    layoutVisible()
                }
            }).also {
            compositeDisposable.add(it)
        }
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
        compositeDisposable.dispose()
        super.onStop()
    }
}
