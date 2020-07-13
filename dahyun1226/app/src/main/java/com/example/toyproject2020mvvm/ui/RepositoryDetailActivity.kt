package com.example.toyproject2020mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityViewRepositoryDetailBindingImpl
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.viewmodel.RepositoryDetailViewModel
import io.reactivex.disposables.CompositeDisposable

class RepositoryDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FULL_NAME = "FullName"
    }

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private val compositeDisposable = CompositeDisposable()

    private val viewModel =
        RepositoryDetailViewModel(repository, compositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewRepositoryDetailBindingImpl =
            DataBindingUtil.setContentView(this, R.layout.activity_view_repository_detail)
        binding.viewModel = viewModel
        binding.activity = this
        viewModel.fullName = intent.getStringExtra(EXTRA_FULL_NAME)
        viewModel.getDetailRepository()

        viewModel.starTextId.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val resId = viewModel.starTextId.get()
                if (resId != null) {
                    viewModel.starText.set(getString(resId))
                }
            }
        })

        viewModel.errorTextId.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val resId = viewModel.errorTextId.get()
                if (resId != null) {
                    viewModel.errorText.set(getString(resId))
                }
            }
        })

        viewModel.languageTextId.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val resId = viewModel.languageTextId.get()
                if (resId != null) {
                    viewModel.languageText.set(getString(resId))
                }
            }
        })

        viewModel.followers.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.followersAndFollowingsText.set(
                    getString(R.string.followers) + viewModel.followers.get() + getString(
                        R.string.followings
                    ) + viewModel.followings.get()
                )
            }
        })

        viewModel.followings.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.followersAndFollowingsText.set(
                    getString(R.string.followers) + viewModel.followers.get() + getString(
                        R.string.followings
                    ) + viewModel.followings.get()
                )
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
