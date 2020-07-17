package com.example.toyproject2020mvvm.binding

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity.Companion.EXTRA_FULL_NAME
import com.example.toyproject2020mvvm.ui.recyclerview.ItemAdapter

@BindingAdapter("bind_items")
fun setBindItems(view: RecyclerView, adapter: ItemAdapter) {
    view.adapter = adapter
}

@BindingAdapter("bind_image")
fun setImage(view: ImageView, url: String) {
    Glide.with(view).load(url)
        .into(view)
}
