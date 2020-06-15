package com.example.toyproject.data.injection

import com.example.toyproject.data.api.ApiProvider
import com.example.toyproject.repository.RepoRepository
import com.example.toyproject.repository.RepoRepositoryImpl
import com.example.toyproject.repository.fake.FakeRepoRepositoryImpl

object Injection {

    fun provideRepoRepository(): RepoRepository {
        return RepoRepositoryImpl(
            ApiProvider.provideRepoApi(),
            ApiProvider.provideUserApi()
        )
    }

    fun provideFakeRepoRepository(): RepoRepository {
        return FakeRepoRepositoryImpl()
    }
}