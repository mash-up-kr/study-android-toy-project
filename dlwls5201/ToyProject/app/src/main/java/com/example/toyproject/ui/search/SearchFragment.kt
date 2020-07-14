package com.example.toyproject.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import com.example.toyproject.R
import com.example.toyproject.data.injection.Injection
import com.example.toyproject.databinding.FragmentSearchBinding
import com.example.toyproject.ui.MainActivity
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

    private val searchModel by lazy {
        SearchViewModel(
            Injection.provideRepoRepository(),
            compositeDisposable
        )
    }

    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.model = searchModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        initEditText()
        initObserve()
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
                        searchModel.searchRepository(requireContext(), query)
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        })
    }

    private fun initObserve() {
        searchModel.items.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                searchModel.items.get()?.let {
                    repoAdapter.setItems(it)
                }
            }
        })

        searchModel.isKeyboard.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (searchModel.isKeyboard.get() == false) {
                    AppUtils.hideSoftKeyBoard(requireActivity())
                }
            }
        })
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}