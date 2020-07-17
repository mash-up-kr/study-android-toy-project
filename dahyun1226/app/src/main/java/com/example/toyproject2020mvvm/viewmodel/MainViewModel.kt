package com.example.toyproject2020mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.ui.recyclerview.ItemAdapter
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val adapter = ItemAdapter(this)

    val loadingVisible = MutableLiveData<Boolean>(false)

    val recyclerVisible = MutableLiveData<Boolean>(false)

    val errorTextVisible = MutableLiveData<Boolean>(false)

    val errorTextId = MutableLiveData<Int>(R.string.error)

    val searchText = MutableLiveData<String>()

    val repoData = MutableLiveData<List<GithubRepoData>>()

    val toastField = MutableLiveData<Int>(R.string.error)

    fun search() {
        if (searchText.value.isNullOrEmpty()) {
            toastField.postValue(R.string.put_contents)
        } else {
            showLoading()
            repository.githubSearch(
                searchText.value!!,
                object : BaseResponse<GithubResponseData> {
                    override fun onSuccess(data: GithubResponseData) {
                        if (data.totalCount == 0) {
                            hideRecycler()
                            showError(R.string.no_response)
                        } else {
                            showRecycler()
                            repoData.postValue(data.items)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        hideLoading()
                        hideRecycler()
                        showError(R.string.error)
                    }

                    override fun onLoading() {
                        hideRecycler()
                        showLoading()
                        hideError()
                    }

                    override fun onLoaded() {
                        hideLoading()
                        showRecycler()
                        hideError()
                    }
                }).also {
                compositeDisposable.add(it)
            }
        }
    }


    fun showLoading() {
        loadingVisible.postValue(true)
    }

    fun hideLoading() {
        loadingVisible.postValue(false)
    }

    fun showRecycler() {
        recyclerVisible.postValue(true)
    }

    fun hideRecycler() {
        recyclerVisible.postValue(false)
    }

    fun showError(errorContent: Int) {
        errorTextId.postValue(errorContent)
        errorTextVisible.postValue(true)
    }

    fun hideError() {
        errorTextVisible.postValue(false)
    }

}


