package com.example.toyproject2020mvvm.util

import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.repository.GitRepositoryImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface

object GitRepositoryInjector {

    fun provideGitRepository(): GitRepositoryInterface {

        return GitRepositoryImpl(RetrofitService.getService())

    }
}