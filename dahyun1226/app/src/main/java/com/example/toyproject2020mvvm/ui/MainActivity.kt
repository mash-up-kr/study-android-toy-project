package com.example.toyproject2020mvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.ActivityMainBinding
import com.example.toyproject2020mvvm.model.repository.GitRepositoryInterface
import com.example.toyproject2020mvvm.util.GitRepositoryInjector
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val repository: GitRepositoryInterface = GitRepositoryInjector.provideGitRepository()

    private val mainViewModel: MainViewModel =
        MainViewModel(repository, compositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel.toastField.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val resId = mainViewModel.toastField.get()
                if (resId != null) {
                    Toast.makeText(this@MainActivity, resId, Toast.LENGTH_SHORT).show()
                }
            }
        })
        mainViewModel.errorTextId.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val resId = mainViewModel.errorTextId.get()
                if (resId != null) {
                    mainViewModel.errorText.set(getString(resId))
                }
            }
        })
        binding.viewModel = mainViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}

