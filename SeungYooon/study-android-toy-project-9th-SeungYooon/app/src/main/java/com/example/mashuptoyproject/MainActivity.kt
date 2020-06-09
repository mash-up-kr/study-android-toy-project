package com.example.mashuptoyproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mashuptoyproject.network.RateLimitInterceptor
import com.example.mashuptoyproject.network.RetrofitHelper
import com.example.mashuptoyproject.network.model.GithubItem
import com.example.mashuptoyproject.network.model.Item
import com.example.mashuptoyproject.util.GitAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }

    private val rateLimitInterceptor  = RateLimitInterceptor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
    }

    private fun loadData() {
        imgSearch.setOnClickListener {
            if (editSearch.text.isEmpty()) {
                Toast.makeText(this, R.string.empty_search, Toast.LENGTH_LONG).show()
            } else {
                progress_circular.visibility = View.VISIBLE

                RetrofitHelper.getApi().requestData(editSearch.text.toString())
                    .enqueue(object : Callback<GithubItem> {
                        override fun onResponse(
                            call: Call<GithubItem>,
                            response: Response<GithubItem>
                        ) {
                            if (response.isSuccessful) {

                                progress_circular.visibility = View.GONE

                                val body = response.body()

                                if (body != null) {

                                    txtNoResponse.visibility = View.INVISIBLE
                                    recyclerView.visibility = View.VISIBLE

                                    body.let {
                                        setAdapter(it.items)
                                    }

                                    if (body.total_count == 0) {
                                        Toast.makeText(this@MainActivity, "No result for request!",Toast.LENGTH_SHORT).show()
                                        recyclerView.visibility = View.INVISIBLE
                                        txtNoResponse.visibility = View.VISIBLE
                                    }
                                }
                            } else {
                                throw IllegalArgumentException("Server Error")
                                rateLimitInterceptor
                                disconnected()
                            }
                        }

                        override fun onFailure(call: Call<GithubItem>, t: Throwable) {
                            Log.v("GithubAPI::", "Failed API call with call: " + call + " + exception: " + t)
                            disconnected()
                        }
                    })
            }
        }
    }

    private fun setAdapter(itemList: List<Item>) {
        val mAdapter = GitAdapter(itemList)
        recyclerView.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm
        recyclerView.setHasFixedSize(true)
        mAdapter.notifyDataSetChanged()
    }

    private fun connected() {
        recyclerView.visibility = View.VISIBLE
        txtNoConnection.visibility = View.INVISIBLE
        loadData()
    }

    private fun disconnected() {
        recyclerView.visibility = View.INVISIBLE
        txtNoConnection.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}
