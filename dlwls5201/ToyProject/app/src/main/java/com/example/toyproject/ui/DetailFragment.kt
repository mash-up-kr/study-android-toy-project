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
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.injection.Injection
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.model.RepoDetailItem
import kotlinx.android.synthetic.main.fragment_detail.*

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

    private val repoRepository: RepoRepository = Injection.provideRepoRepository()

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

        repoRepository.getDetailRepository(ownerName, repo, object : BaseResponse<RepoDetailModel> {
            override fun onSuccess(data: RepoDetailModel) {
                setRepoDetailData(data.mapToPresentation(requireContext()))
            }

            override fun onFail(description: String) {
                showError(description)
            }

            override fun onError(throwable: Throwable) {
                showError(throwable.message)
            }

            override fun onLoading() {
                hideError()
                showProgress()
            }

            override fun onLoaded() {
                hideProgress()
            }
        })
    }

    private fun setRepoDetailData(repoDetailItem: RepoDetailItem) {
        Glide.with(requireContext())
            .load(repoDetailItem.ownerUrl)
            .placeholder(ColorDrawable(Color.GRAY))
            .error(ColorDrawable(Color.RED))
            .into(ivProfile)

        tvTitle.text = repoDetailItem.title
        tvStars.text = repoDetailItem.stars
        tvDescription.text = repoDetailItem.description
        tvLanguage.text = repoDetailItem.language

        tvFollower.text = repoDetailItem.followers
        tvFollowing.text = repoDetailItem.following
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
}
