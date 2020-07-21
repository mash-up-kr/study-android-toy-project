package com.example.toyproject2020mvvm

import android.app.Application
import com.example.toyproject2020mvvm.di.appModule
import com.example.toyproject2020mvvm.model.api.RetrofitService
import com.example.toyproject2020mvvm.model.api.RetrofitServiceInterface
import com.example.toyproject2020mvvm.model.repository.GitRepositoryImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.MainViewModelFactory
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.RepositoryDetailViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}

