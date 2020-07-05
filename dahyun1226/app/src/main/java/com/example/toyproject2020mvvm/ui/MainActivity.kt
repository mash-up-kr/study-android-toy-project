package com.example.toyproject2020mvvm.ui

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityMainBinding
import com.example.toyproject2020mvvm.model.BaseResponse
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.model.data.GithubResponseData
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.ui.recyclerview.ItemAdapter
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private val mainViewModel: MainViewModel = MainViewModel(repository, compositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}

