package com.example.toyproject2020mvvm.ui.recyclerview


import android.content.Intent
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyproject2020mvvm.R
import com.example.toyproject2020mvvm.databinding.RecyclerviewItemBinding
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity.Companion.EXTRA_FULL_NAME
import com.example.toyproject2020mvvm.model.data.GithubRepoData
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity
import com.example.toyproject2020mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class ItemAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun getItemCount() = viewModel.repoData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, position);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MainViewModel, pos: Int) {
            binding.viewModel = viewModel
            binding.pos = pos;
        }
    }
}
