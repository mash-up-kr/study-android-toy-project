package com.example.toyproject2020mvvm.viewmodel

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
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

    val loadingVisible = ObservableField(false)

    val recyclerVisible = ObservableField(false)

    val errorTextVisible = ObservableField(false)

    val errorText = ObservableField("")

    val searchText = ObservableField("")

    var repoData = ObservableArrayList<GithubRepoData>()

    fun search(context: Context) {
        if (searchText.get() == null || searchText.get() == "") {
            Toast.makeText(context, context.getString(R.string.put_contents), Toast.LENGTH_SHORT)
                .show()
        } else {
            loadingVisible()
            repository.githubSearch(
                searchText.get()!!,
                object : BaseResponse<GithubResponseData> {
                    override fun onSuccess(data: GithubResponseData) {
                        if (data.totalCount == 0) {
                            recyclerInvisible()
                            errorVisible(context, context.getString(R.string.no_response))
                        } else {
                            recyclerVisible()
                            repoData.clear()
                            repoData.addAll(data.items)
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        loadingInvisible()
                        recyclerInvisible()
                        errorVisible(context, throwable.toString())
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

    fun errorVisible(context: Context, errorContent: String) {
        errorText.set(context.getString(R.string.error) + errorContent)
        errorTextVisible.set(true)
    }

    fun errorInvisible() {
        errorTextVisible.set(false)
    }
}

@BindingAdapter("bind_items")
fun setBindiItems(view: RecyclerView, items: List<GithubRepoData>) {
    view.adapter = ItemAdapter(items)
}