package com.example.toyproject2020mvvm.viewmodel

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity.Companion.EXTRA_FULL_NAME
import com.example.toyproject2020mvvm.ui.recyclerview.ItemAdapter
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val adapter = ItemAdapter(this)

    val loadingVisible = ObservableField(false)

    val recyclerVisible = ObservableField(false)

    val errorTextVisible = ObservableField(false)

    val errorText = ObservableField("")

    val errorTextId = ObservableField(0)

    val searchText = ObservableField("")

    val repoData = ObservableArrayList<GithubRepoData>()

    val toastField = ObservableField(0)

    fun search() {
        if (searchText.get().isNullOrEmpty()) {
            toastField.set(R.string.put_contents)
        } else {
            showLoading()
            repository.githubSearch(
                searchText.get()!!,
                object : BaseResponse<GithubResponseData> {
                    override fun onSuccess(data: GithubResponseData) {
                        if (data.totalCount == 0) {
                            hideRecycler()
                            showError(R.string.no_response)
                        } else {
                            showRecycler()
                            repoData.clear()
                            repoData.addAll(data.items)
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
        loadingVisible.set(true)
    }

    fun hideLoading() {
        loadingVisible.set(false)
    }

    fun showRecycler() {
        recyclerVisible.set(true)
    }

    fun hideRecycler() {
        recyclerVisible.set(false)
    }

    fun showError(errorContent: Int) {
        errorTextId.set(errorContent)
        errorTextVisible.set(true)
    }

    fun hideError() {
        errorTextVisible.set(false)
    }

    fun seeDetailRepo(view: View, pos: Int) {
        val intent = Intent(view.context, RepositoryDetailActivity::class.java)
        intent.putExtra(EXTRA_FULL_NAME, repoData[pos].fullName)
        view.context.startActivity(intent)
    }
}

@BindingAdapter("bind_items")
fun setBindItems(view: RecyclerView, adapter: ItemAdapter) {
    view.adapter = adapter
}

@BindingAdapter(value = ["bind_recycler_viewmodel", "bind_recycler_pos"])
fun setImage(view: ImageView, viewModel: MainViewModel, pos: Int) {
    Glide.with(view).load(viewModel.repoData[pos].owner.avatarUrl)
        .into(view)
}

