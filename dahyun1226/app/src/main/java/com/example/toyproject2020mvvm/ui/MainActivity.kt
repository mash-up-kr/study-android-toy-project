package com.example.toyproject2020mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityMainBinding
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.util.ResourceProvider
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private val resourceProvider = ResourceProvider(this)

    private val mainViewModel: MainViewModel =
        MainViewModel(resourceProvider, repository, compositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}

