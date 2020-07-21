package com.example.toyproject2020mvvm.di

import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.api.RetrofitServiceInterface
import com.example.toyproject2020mvvm.model.repository.GitRepositoryImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.MainViewModelFactory
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.RepositoryDetailViewModelFactory
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
    //MainViewModelFactory
    single {
        MainViewModelFactory(get())
    }
    //RepositoryDetailViewModelFactory
    single {
        RepositoryDetailViewModelFactory(get())
    }
}


val appModule = listOf(modelPart)

