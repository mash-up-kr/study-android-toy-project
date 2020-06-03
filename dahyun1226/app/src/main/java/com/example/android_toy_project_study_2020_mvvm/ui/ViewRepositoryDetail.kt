package com.example.android_toy_project_study_2020_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.ViewRepositoryDetailModel
import kotlinx.android.synthetic.main.activity_view_repository_detail.*
import kotlinx.android.synthetic.main.activity_view_repository_detail.button1
import kotlinx.android.synthetic.main.activity_view_repository_detail.loading

class ViewRepositoryDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_repository_detail)
        button1.setOnClickListener {
            finish()
        }
        val intent = getIntent()
        val fullName=intent.getStringExtra("FullName")
        val userName = fullName.split("/")[0]
        val repoName = fullName.split("/")[1]
        loading.visibility= View.VISIBLE
        layout.visibility= View.INVISIBLE
        val Model: ViewRepositoryDetailModel = ViewRepositoryDetailModel(this)
        Model.getaRepository(userName,repoName)
    }
}
