package com.example.toyproject2020mvvm.di

import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.api.RetrofitServiceInterface
import com.example.toyproject2020mvvm.model.repository.GitRepositoryImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.RepositoryDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modelPart = module {
    //retrofit
    single<RetrofitServiceInterface> {
        RetrofitService.getService()
    }
    //repository
    factory<GitRepositoryInterface>{
        GitRepositoryImpl(get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        RepositoryDetailViewModel(get())
    }
}


val appModule = listOf(modelPart)

