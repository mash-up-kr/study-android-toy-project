package com.example.android_toy_project_study_2020_mvvm.ui

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.MainActivityModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.visibility = INVISIBLE
        searchButton.setOnClickListener {
            if (editText.text.toString().trim().isEmpty()) {
                Toast.makeText(this,"내용을 입력해주세요!",Toast.LENGTH_SHORT).show()
            } else {
                loading.visibility = VISIBLE
                val model = MainActivityModel(this)
                model.githubSearch(editText.text.toString())
            }
        }
    }
}

