package com.example.android_toy_project_study_2020_mvvm.recyclerview


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.android_toy_project_study_2020_mvvm.R
import com.example.android_toy_project_study_2020_mvvm.ui.ViewRepositoryDetail
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class ItemAdapter (private var items: ArrayList<gitItem>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            val intent = Intent(it.context,ViewRepositoryDetail::class.java)
            intent.putExtra("FullName",item.full_name)
            it.context.startActivity(intent)
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ItemAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: gitItem) {
            view.textView.text=item.full_name
            if(item.language==null)
            {view.textView2.text="No language specified"}
            else{view.textView2.text=item.language}
            view.setOnClickListener(listener)
            Glide.with(view).load(item.avatar_url).into(view.imageView)
        }
    }

}