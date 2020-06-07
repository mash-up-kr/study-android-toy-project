package com.example.mvc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvc.R
import com.example.mvc.api.GithubApiProvider
import com.example.mvc.extention.*
import com.example.mvc.model.SearchResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val mainAdapter : MainAdapter by lazy { MainAdapter{ clickCallback(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initRecyclerAdapter()
        searchViewListener()
    }

    private fun initRecyclerAdapter() {
        rvMain.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun clickCallback(position: Int) {
        val data = mainAdapter.getItem(position)

        Intent(this, DetailActivity::class.java).apply {
            putExtra(USER_NAME, data?.owner?.login)
            putExtra(REPO_NAME, data?.name)
            startActivity(this)
        }
    }

    private fun searchViewListener() {
        svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(this@MainActivity.isNetworkConnected()) getSearchResponse(query)
                else clError.visible()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (svMain.query.isEmpty()) this@MainActivity.toast("검색어를 입력해주세요")
                return false
            }
        })
    }

    private fun getSearchResponse(query: String?) {
        clLoading.visible()
        query?.let {
            val getSearchResponse : Call<SearchResponse> =
                GithubApiProvider.getGithubService().searchRepository(query)
            getSearchResponse.enqueue(object : Callback<SearchResponse>{
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) { Log.e(TAG, t.toString()) }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    when {
                        response.isSuccessful-> {
                            clLoading.gone()
                            clError.gone()
                            when {
                                response.body()?.items.isNullOrEmpty() -> tvNoResult.visible()
                                else -> {
                                    tvNoResult.gone()
                                    response.body()?.items?.let { data -> mainAdapter.setData(data) }
                                }
                            }
                        }
                        else -> {
                            this@MainActivity.hideKeyboard(svMain)
                            svMain.gone()
                            rvMain.gone()
                            clLoading.gone()
                            Log.e(TAG, response.message()) }
                    }
                }
            })
        }
    }

    companion object{
        const val USER_NAME: String = "USER_NAME"
        const val REPO_NAME: String = "REPO_NAME"
    }
}
