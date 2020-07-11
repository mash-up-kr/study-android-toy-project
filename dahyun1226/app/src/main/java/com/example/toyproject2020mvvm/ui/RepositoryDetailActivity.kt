package com.example.toyproject2020mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityViewRepositoryDetailBinding
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.util.ResourceProvider
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.RepositoryDetailViewModel
import io.reactivex.disposables.CompositeDisposable

class RepositoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private val resourceProvider = ResourceProvider(this)

    private val compositeDisposable = CompositeDisposable()

    private val viewModel =
        RepositoryDetailViewModel(resourceProvider, repository, compositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fullName = intent.getStringExtra(EXTRA_FULL_NAME)
        viewModel.getDetailRepository()
        val binding: ActivityViewRepositoryDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_view_repository_detail)
        binding.viewModel = viewModel
        binding.activity = this
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
