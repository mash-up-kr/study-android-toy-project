package com.example.android_toy_project_study_2020_mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.MainModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton.setOnClickListener {
            val Model:MainModel= MainModel()
            Model.githubSearch(editText.text.toString())
        }
    }
}
