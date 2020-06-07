package com.example.mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvc.R
import com.example.mvc.api.GithubApiProvider
import com.example.mvc.databinding.ActivityDetailBinding
import com.example.mvc.extention.*
import com.example.mvc.model.Repo
import com.example.mvc.model.User
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private var userName: String = ""
    private var repoName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this,R.layout.activity_detail)

        setIntentData()
        if(this.isNetworkConnected()) {
            getRepository()
            getUser()
        }else{
            clLoadingDetail.gone()
            clItemContainer.gone()
            clErrorDetail.visible()
        }
    }

    private fun setIntentData() {
        userName = intent.getStringExtra(MainActivity.USER_NAME)
        repoName = intent.getStringExtra(MainActivity.REPO_NAME)
    }

    private fun getRepository() {
        if(userName != "" && repoName != "") {
            val getRepository : Call<Repo> =GithubApiProvider.getGithubService().getRepository(userName, repoName)

            getRepository.enqueue(object : Callback<Repo>{
                override fun onFailure(call: Call<Repo>, t: Throwable) { Log.e(TAG, t.toString()) }

                override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            clLoadingDetail.gone()
                            clErrorDetail.gone()
                            clItemContainer.visible()
                            binding.detailRepoVM = it
                            ivUserDetail.loadImage(it.owner?.avatarUrl)
                        }
                    }else{
                        clLoadingDetail.gone()
                        clItemContainer.gone()
                        Log.e(TAG, response.message())
                    }
                }
            })
        }
    }

    private fun getUser() {
        clLoadingDetail.visible()
        if(userName != "") {
            val getUser : Call<User> =GithubApiProvider.getGithubService().getUser(userName)

            getUser.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) { Log.e(TAG, t.toString()) }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        clLoadingDetail.gone()
                        clErrorDetail.gone()
                        clItemContainer.visible()
                        response.body()?.let { binding.detailUserVM = it }
                    }else{
                        clLoadingDetail.gone()
                        clItemContainer.gone()
                        Log.e(TAG, response.message())
                    }
                }
            })
        }
    }
}
