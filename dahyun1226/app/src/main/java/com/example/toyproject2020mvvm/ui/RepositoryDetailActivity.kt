package com.example.toyproject2020mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityViewRepositoryDetailBindingImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import com.example.toyproject2020mvvm.viewmodel.RepositoryDetailViewModel
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.MainViewModelFactory
import com.example.toyproject2020mvvm.viewmodel.viewmodelfactory.RepositoryDetailViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class RepositoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    private val repository: GitRepositoryInterface by inject()

    private lateinit var viewModel: RepositoryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewRepositoryDetailBindingImpl =
            DataBindingUtil.setContentView(this, R.layout.activity_view_repository_detail)
        viewModel = ViewModelProvider(
            this,
            RepositoryDetailViewModelFactory(repository)
        ).get(RepositoryDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.activity = this
        binding.lifecycleOwner = this
        viewModel.getDetailRepository(intent.getStringExtra(EXTRA_FULL_NAME))
    }

}
