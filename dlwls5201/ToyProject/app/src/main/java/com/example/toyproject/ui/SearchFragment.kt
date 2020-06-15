package com.example.toyproject.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.toyproject.R
import com.example.toyproject.base.ext.toast
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.injection.Injection
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.adapter.RepositoryAdapter
import com.example.toyproject.utils.AppUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    companion object {

        fun newInstance() = SearchFragment()
    }

    private val repoAdapter by lazy {
        RepositoryAdapter().apply {
            onItemClick = {
                (requireActivity() as MainActivity).goToDetailFragment(
                    it.owner.ownerName,
                    it.repoName
                )
            }
        }
    }

    private val repoRepository: RepoRepository = Injection.provideRepoRepository()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        initEditText()
        initButton()
    }

    private fun initRecyclerView() {
        listSearchRepository.adapter = repoAdapter
    }

    private fun initEditText() {
        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val query = v?.text.toString()
                        searchRepository(query)
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        })
    }

    private fun initButton() {
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString()
            searchRepository(query)
        }
    }

    private fun searchRepository(query: String) {
        if (query.isEmpty()) {
            requireContext().toast(requireContext().getString(R.string.please_write_repository_name))
        } else {
            repoRepository.searchRepositories(query, object : BaseResponse<RepoSearchResponse> {
                override fun onSuccess(data: RepoSearchResponse) {
                    with(repoAdapter) {
                        setItems(data.items.map { it.mapToPresentation(requireContext()) })
                    }

                    if (0 == data.totalCount) {
                        showError(getString(R.string.no_search_result))
                    }
                }

                override fun onFail(description: String) {
                    showError(description)
                }

                override fun onError(throwable: Throwable) {
                    showError(throwable.message)
                }

                override fun onLoading() {
                    hideKeyboard()
                    clearResults()
                    hideError()
                    showProgress()
                }

                override fun onLoaded() {
                    hideProgress()
                }

            }).also {
                compositeDisposable.add(it)
            }
        }
    }

    private fun hideKeyboard() {
        AppUtils.hideSoftKeyBoard(requireActivity())
    }

    private fun clearResults() {
        repoAdapter.clearItems()
    }

    private fun showProgress() {
        pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pbLoading.visibility = View.GONE
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

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}