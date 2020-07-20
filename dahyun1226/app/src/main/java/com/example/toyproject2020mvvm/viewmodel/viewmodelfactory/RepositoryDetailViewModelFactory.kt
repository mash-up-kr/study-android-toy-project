package com.example.toyproject2020mvvm.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.RepositoryDetailViewModel
import io.reactivex.disposables.CompositeDisposable

class RepositoryDetailViewModelFactory(
    private val repository: GitRepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RepositoryDetailViewModel::class.java)) {
            RepositoryDetailViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}