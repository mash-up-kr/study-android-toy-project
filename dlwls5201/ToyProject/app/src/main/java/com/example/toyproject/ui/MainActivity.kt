package com.example.toyproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toyproject.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTitle()
        initMainFragment()
    }

    private fun initTitle() {
        title = getString(R.string.search_repository)
    }

    private fun initMainFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.lylFragmentContainer, SearchFragment.newInstance())
            .commit()
    }

    fun goToDetailFragment(ownerName: String, repoName: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.lylFragmentContainer, DetailFragment.newInstance(ownerName, repoName))
            .addToBackStack(null)
            .commit()
    }
}
