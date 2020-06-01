package com.example.android_toy_project_study_2020_mvvm.ui

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.MainModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.visibility= INVISIBLE
        searchButton.setOnClickListener {
            loading.visibility=VISIBLE
            val Model:MainModel= MainModel(this)
            Model.githubSearch(editText.text.toString())
        }
    }
}

