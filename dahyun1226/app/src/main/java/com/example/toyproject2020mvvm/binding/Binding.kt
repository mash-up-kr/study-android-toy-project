package com.example.toyproject2020mvvm.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
