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
import com.example.toyproject.base.util.Dlog
import com.example.toyproject.data.api.ApiProvider
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.ui.adapter.RepositoryAdapter
import com.example.toyproject.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val repoApi = ApiProvider.provideRepoApi()

    private var repoCall: Call<RepoSearchResponse>? = null

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
        Dlog.d("query : $query")

        if (query.isEmpty()) {
            //토스트를 보여줍니다.
            requireContext().toast(requireContext().getString(R.string.please_write_repository_name))
        } else {
            //키보드를 내립니다.
            hideKeyboard()
            //이전 결과를 지웁니다.
            clearResults()
            //에러표시를 숨깁니다.
            hideError()
            //로딩화면을 보여줍니다.
            showProgress()

            //서버로부터 검색된 리포지터리를 가져옵니다.
            repoCall = repoApi.searchRepository(query)
            repoCall?.enqueue(object : Callback<RepoSearchResponse> {

                override fun onResponse(
                    call: Call<RepoSearchResponse>,
                    response: Response<RepoSearchResponse>
                ) {
                    //로딩화면을 숨깁니다.
                    hideProgress()

                    //통신에 성공하면 검색된 리스트를 화면에 보여줍니다.
                    val body = response.body()
                    if (response.isSuccessful && null != body) {
                        with(repoAdapter) {
                            setItems(body.items.map { it.mapToPresentation(requireContext()) })
                        }

                        if (0 == body.totalCount) {
                            showError(getString(R.string.no_search_result))
                        }
                    } else {
                        showError(response.message())
                    }
                }

                override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                    hideProgress()
                    showError(t.message)
                }
            })
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
        super.onStop()
        repoCall?.cancel()
    }

}