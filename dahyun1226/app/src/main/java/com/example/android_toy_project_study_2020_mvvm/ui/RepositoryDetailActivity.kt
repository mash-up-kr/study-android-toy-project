package com.example.android_toy_project_study_2020_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.RepositoryDetailActivityController
import kotlinx.android.synthetic.main.activity_view_repository_detail.bt_backButton

class RepositoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repository_detail)
        val model = RepositoryDetailActivityController(this)
        bt_backButton.setOnClickListener {
            finish()
        }
        val intentFullName = "FullName"
        val fullName = intent.getStringExtra(intentFullName)
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        model.getRepository(userName,repoName)
    }
}
