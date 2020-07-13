package com.example.toyproject.ui.search

import android.content.Context
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.toyproject.R
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.model.RepoItem
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val searchRepository: RepoRepository,
    private val compositeDisposable: CompositeDisposable
) {

    //로딩 필드
    val isLoading = ObservableField(false)

    //키보드 필드
    val isKeyboard = ObservableField(false)

    //검색 버튼 활성 필드
    val enableSearchButton = ObservableField(true)

    //에러 메시지 필드
    val errorMessage = ObservableField("")

    //검색 입력 필드
    val editSearchText = ObservableField("")

    //리포지터리 아이템 필드
    val items = ObservableField<List<RepoItem>>(emptyList())

    init {
        editSearchText.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                //사용자가 입력한 값을 관찰하고 있어 실시간으로 데이터를 받아올 수 있습니다.
                val query = editSearchText.get()
                if (query.isNullOrEmpty()) {
                    enableSearchButton.set(false)
                } else {
                    enableSearchButton.set(true)
                }
            }
        })
    }

    fun searchRepository(context: Context, query: String) {
        hideKeyboard()

        searchRepository.searchRepositories(query, object : BaseResponse<RepoSearchResponse> {
            override fun onSuccess(data: RepoSearchResponse) {
                items.set(data.items.map { it.mapToPresentation(context) })

                if (0 == data.totalCount) {
                    showErrorMessage(context.getString(R.string.no_search_result))
                }
            }

            override fun onFail(description: String) {
                Log.d("MyTag", "description : $description")
                showErrorMessage(description)
            }

            override fun onError(throwable: Throwable) {
                Log.d("MyTag", "throwable : ${throwable.message}")
                showErrorMessage(throwable.message ?: context.getString(R.string.unexpected_error))
            }

            override fun onLoading() {
                clearItems()
                showLoading()
                hideErrorMessage()
            }

            override fun onLoaded() {
                hideLoading()
            }
        }).also {
            compositeDisposable.add(it)
        }
    }

    private fun clearItems() {
        items.set(emptyList())
    }

    private fun showLoading() {
        isLoading.set(true)
    }

    private fun hideLoading() {
        isLoading.set(false)
    }

    private fun hideKeyboard() {
        isKeyboard.set(false)
        isKeyboard.notifyChange()
    }

    private fun showErrorMessage(error: String) {
        errorMessage.set(error)
    }

    private fun hideErrorMessage() {
        errorMessage.set("")
    }
}