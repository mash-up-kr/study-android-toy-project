package com.example.toyproject2020mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityMainBinding
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.MainViewModelFactory
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository, compositeDisposable)).get(
                MainViewModel::class.java
            )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        mainViewModel.toastField.observe(this, Observer {
            Toast.makeText(this, R.string.put_contents, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}

