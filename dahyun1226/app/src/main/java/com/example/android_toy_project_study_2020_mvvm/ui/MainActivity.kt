package com.example.android_toy_project_study_2020_mvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.api.MainActivityController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model = MainActivityController(this)
        bt_SearchButton.setOnClickListener {
            if (et_EditText.text.toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.put_contents), Toast.LENGTH_SHORT).show()
            } else {
                model.loadingOnOffMainActivity()
                model.githubSearch(et_EditText.text.toString())
            }
        }
    }
}

