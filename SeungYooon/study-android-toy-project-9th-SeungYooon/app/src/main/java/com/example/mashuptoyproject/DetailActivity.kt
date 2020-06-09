package com.example.mashuptoyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mashuptoyproject.network.RetrofitHelper
import com.example.mashuptoyproject.network.RetrofitService
import com.example.mashuptoyproject.network.model.GithubItem
import com.example.mashuptoyproject.network.model.Item
import com.example.mashuptoyproject.network.model.User
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Response
import java.lang.IllegalArgumentException
import javax.security.auth.callback.Callback

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val REPO = "repo"
        private const val OWNER = "owner"
        private const val AVATAR_URL = "avatar_url"
    }

    private val repo: String by lazy { intent.getStringExtra(REPO) }
    private val owner: String by lazy { intent.getStringExtra(OWNER) }
    private val avatarurl: String by lazy { intent.getStringExtra(AVATAR_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent != null) {
            nameDetail.setText("$owner/")
            repoDetail.setText(repo)
            Glide.with(this).load(avatarurl).override(1024)
                .into(imgDetail)
        }

        BackMain.setOnClickListener { finish() }

        loadDetail()

        loadFollow()

        loadFail()
    }

    private fun loadDetail() {
        RetrofitHelper.getApi().requestOwner(owner, repo)
            .enqueue(object : retrofit2.Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {

                            numStars.setText("${body.stargazers_count} stars")
                            descriptionName.setText(body.description)

                            if (body.language == null) {
                                languageName.setText(R.string.no_language)
                            } else {
                                languageName.setText(body.language)
                            }
                        }
                    } else {
                        throw IllegalArgumentException("Server Error")
                        loadFail()
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Log.v(
                        "GithubAPI::", "Failed API call with call: " + call +
                                " + exception: " + t
                    )
                }
            })
    }

    private fun loadFollow() {
        RetrofitHelper.getApi().requestUser(owner)
            .enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {
                            followerNum.setText(body.followers.toString())
                            followingNum.setText(body.following.toString())
                        }
                    } else {
                        throw IllegalArgumentException("error")
                        loadFail()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.v(
                        "GithubAPI::", "Failed API call with call: " + call +
                                " + exception: " + t
                    )
                }
            })
    }

    private fun loadFail() {
        numStars.setText(R.string.no_internet)
        descriptionName.setText(R.string.no_internet)
        languageName.setText(R.string.no_internet)
        followerNum.setText(R.string.no_internet)
        followingNum.setText(R.string.no_internet)
    }
}
