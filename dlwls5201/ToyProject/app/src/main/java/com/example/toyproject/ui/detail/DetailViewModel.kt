package com.example.toyproject.ui.detail

import android.content.Context
import androidx.databinding.ObservableField
import com.example.toyproject.R
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.mapToPresentation
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.ui.model.RepoDetailItem
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(
    private val repoRepository: RepoRepository,
    private val compositeDisposable: CompositeDisposable
) {

    //로딩 필드
    val isLoading = ObservableField(false)

    //에러 메시지 필드
    val errorMessage = ObservableField("")

    //아이템 필드
    val repoDetailItem = ObservableField<RepoDetailItem>()

    fun loadData(context: Context, ownerName: String, repo: String) {

        repoRepository.getDetailRepository(ownerName, repo, object : BaseResponse<RepoDetailModel> {
            override fun onSuccess(data: RepoDetailModel) {
                repoDetailItem.set(data.mapToPresentation(context))
            }

            override fun onFail(description: String) {
                showErrorMessage(description)
            }

            override fun onError(throwable: Throwable) {
                showErrorMessage(throwable.message ?: context.getString(R.string.unexpected_error))
            }

            override fun onLoading() {
                hideErrorMessage()
                showLoading()
            }

            override fun onLoaded() {
                hideLoading()
            }
        }).also {
            compositeDisposable.add(it)
        }
    }

    private fun showLoading() {
        isLoading.set(true)
    }

    private fun hideLoading() {
        isLoading.set(false)
    }

    private fun showErrorMessage(error: String) {
        errorMessage.set(error)
    }

    private fun hideErrorMessage() {
        errorMessage.set("")
    }
}