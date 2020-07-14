package com.example.toyproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.toyproject.R
import com.example.toyproject.base.util.Dlog
import com.example.toyproject.data.injection.Injection
import com.example.toyproject.databinding.FragmentDetailBinding
import io.reactivex.disposables.CompositeDisposable

class DetailFragment : Fragment() {

    companion object {

        private const val ARGUMENT_OWNER_NAME = "owner_name"

        private const val ARGUMENT_REPO = "repo"

        fun newInstance(ownerName: String, repoName: String) = DetailFragment()
            .apply {
                arguments = bundleOf(
                    Pair(ARGUMENT_OWNER_NAME, ownerName),
                    Pair(ARGUMENT_REPO, repoName)
                )
            }
    }

    private val detailModel by lazy {
        DetailViewModel(
            Injection.provideRepoRepository(),
            compositeDisposable
        )
    }

    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.model = detailModel

        return binding.root
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
        detailModel.loadData(requireContext(), ownerName, repo)

    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}
