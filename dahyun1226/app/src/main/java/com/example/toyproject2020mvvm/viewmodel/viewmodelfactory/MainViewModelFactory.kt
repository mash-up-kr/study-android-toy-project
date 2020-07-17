package com.example.toyproject2020mvvm.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModelFactory(
    private val repository: GitRepositoryInterface,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository, compositeDisposable) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}