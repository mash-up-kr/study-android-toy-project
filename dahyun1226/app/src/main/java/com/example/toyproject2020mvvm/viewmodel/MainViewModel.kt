package com.example.toyproject2020mvvm.viewmodel

import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val loadingVisible = ObservableField(false)

    val recyclerVisible = ObservableField(false)

    val errorTextVisible = ObservableField(false)

    val errorText = ObservableField("")

    val searchText = ObservableField("")

    val repoData = ObservableArrayList<GithubRepoData>()

    fun search() {
        if (searchText.get() == null || searchText.get() == "") {
            //Toast.makeText(this, getString(R.string.put_contents), Toast.LENGTH_SHORT).show()
        } else {
            loadingVisible()
            repository.githubSearch(
                searchText.get()!!,
                object : BaseResponse<GithubResponseData> {
                    override fun onSuccess(data: GithubResponseData) {
                        if (data.totalCount == 0) {
                            loadingInvisible()
                            recyclerInvisible()
                            errorVisible(Resources.getSystem().getString(R.string.no_response))
                        } else {
                            recyclerVisible()
//                            rc_activity_main_Recycler.adapter =
//                                ItemAdapter(data.items as ArrayList<GithubRepoData>)
                        }
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
                }).also {
                compositeDisposable.add(it)
            }
        }
    }

    fun loadingVisible() {
        loadingVisible.set(true)
    }

    fun loadingInvisible() {
        loadingVisible.set(false)
    }

    fun recyclerVisible() {
        recyclerVisible.set(true)
    }

    fun recyclerInvisible() {
        recyclerVisible.set(false)
    }

    fun errorVisible(errorContent: String) {
        errorText.set(Resources.getSystem().getString(R.string.error) + errorContent)
        errorTextVisible.set(true)
    }

    fun errorInvisible() {
        errorTextVisible.set(false)
    }
}