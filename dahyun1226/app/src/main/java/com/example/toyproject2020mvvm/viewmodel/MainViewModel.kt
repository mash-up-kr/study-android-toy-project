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
import com.example.toyproject2020mvvm.util.ResourceProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val adapter = ItemAdapter(this)

    val loadingVisible = ObservableField(false)

    val recyclerVisible = ObservableField(false)

    val errorTextVisible = ObservableField(false)

    val errorText = ObservableField("")

    val searchText = ObservableField("")

    val repoData = ObservableArrayList<GithubRepoData>()

    fun search() {
        if (searchText.get() == null || searchText.get() == "") {
            //Toast.makeText(context, context.getString(R.string.put_contents), Toast.LENGTH_SHORT) .show()
        } else {
            loadingVisible()
            repository.githubSearch(
                searchText.get()!!,
                object : BaseResponse<GithubResponseData> {
                    override fun onSuccess(data: GithubResponseData) {
                        if (data.totalCount == 0) {
                            recyclerInvisible()
                            errorVisible(resourceProvider.getString(R.string.no_response))
                        } else {
                            recyclerVisible()
                            repoData.clear()
                            repoData.addAll(data.items)
                            adapter.notifyDataSetChanged()
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
        errorText.set(resourceProvider.getString(R.string.error) + errorContent)
        errorTextVisible.set(true)
    }

    fun errorInvisible() {
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

