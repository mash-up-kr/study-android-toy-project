package com.example.toyproject.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.toyproject.R
import com.example.toyproject.base.util.Dlog
import com.example.toyproject.data.api.ApiProvider
import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.UserModel
import com.example.toyproject.data.model.mapToView
import com.example.toyproject.ui.model.RepoItem
import com.example.toyproject.ui.model.UserItem
import kotlinx.android.synthetic.main.fragment_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    companion object {

        private const val ARGUMENT_OWNER_NAME = "owner_name"

        private const val ARGUMENT_REPO = "repo"

        fun newInstance(ownerName: String, repoName: String) = DetailFragment().apply {
            arguments = bundleOf(
                Pair(ARGUMENT_OWNER_NAME, ownerName),
                Pair(ARGUMENT_REPO, repoName)
            )
        }
    }

    private val repoApi = ApiProvider.provideRepoApi()

    private val userApi = ApiProvider.provideUserApi()

    private var repoCall: Call<RepoModel>? = null

    private var userCall: Call<UserModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val ownerName = arguments?.getString(ARGUMENT_OWNER_NAME) ?: throw IllegalArgumentException(
            "No owner name info exists in extras"
        )

        val repo = arguments?.getString(ARGUMENT_REPO) ?: throw IllegalArgumentException(
            "No repo info exists in extras"
        )

        Dlog.d("ownerName : $ownerName , repo : $repo")
        loadData(ownerName, repo)

    }

    private fun loadData(ownerName: String, repo: String) {
        //에러표시를 숨깁니다.
        hideError()
        //로딩화면을 보여줍니다.
        showProgress()

        //repo 데이터를 로딩합니다.
        loadRepoData(ownerName, repo)

        //user 데이터 로딩을 합니다.
        loadUserData(ownerName)
    }

    private fun loadRepoData(ownerName: String, repo: String) {
        repoCall = repoApi.getRepository(ownerName, repo)
        repoCall?.enqueue(object : Callback<RepoModel> {
            override fun onResponse(call: Call<RepoModel>, response: Response<RepoModel>) {
                //로딩 화면을 숨깁니다.
                hideProgress()

                val body = response.body()
                if (response.isSuccessful && null != body) {
                    //repo 데이터를 화면에 보여줍니다.
                    setRepoData(body.mapToView(requireContext()))
                } else {
                    //에러를 표시합니다.
                    showError(response.message())
                }
            }

            override fun onFailure(call: Call<RepoModel>, t: Throwable) {
                hideProgress()
                showError(t.message)
            }
        })
    }

    private fun setRepoData(repoItem: RepoItem) {
        Glide.with(requireContext())
            .load(repoItem.owner.ownerUrl)
            .placeholder(ColorDrawable(Color.GRAY))
            .error(ColorDrawable(Color.RED))
            .into(ivProfile)

        tvTitle.text = repoItem.title
        tvStars.text = repoItem.stars
        tvDescription.text = repoItem.description
        tvLanguage.text = repoItem.language
    }

    private fun loadUserData(userName: String) {
        userCall = userApi.getUser(userName)
        userCall?.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                //로딩 화면을 숨깁니다.
                hideProgress()

                val body = response.body()
                if (response.isSuccessful && null != body) {
                    //user 데이터를 화면에 보여줍니다.
                    setUserData(body.mapToView(requireContext()))
                } else {
                    //에러를 표시합니다.
                    showError(response.message())
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                hideProgress()
                showError(t.message)
            }
        })
    }

    private fun setUserData(userItem: UserItem) {
        tvFollower.text = userItem.followers
        tvFollowing.text = userItem.following
    }

    private fun showError(message: String?) {
        with(tvMessage) {
            text = message ?: context.getString(R.string.unexpected_error)
            visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        with(tvMessage) {
            text = ""
            visibility = View.GONE
        }
    }

    private fun showProgress() {
        tvMessage.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pbLoading.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        repoCall?.cancel()
        userCall?.cancel()
    }
}
