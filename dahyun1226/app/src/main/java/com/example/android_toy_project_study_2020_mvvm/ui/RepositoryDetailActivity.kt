package com.example.android_toy_project_study_2020_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.ViewRepositoryDetailModel
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import kotlinx.android.synthetic.main.activity_view_repository_detail.backButton
import kotlinx.android.synthetic.main.activity_view_repository_detail.loading

class RepositoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repository_detail)
        val model = ViewRepositoryDetailModel(this)
        backButton.setOnClickListener {
            finish()
        }
        val fullName = intent.getStringExtra("FullName")
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        model.getRepository(userName,repoName)
    }
}
