package com.example.mvc.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc.R
import com.example.mvc.databinding.ItemMainBinding
import com.example.mvc.extention.loadImage
import com.example.mvc.model.Repo
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(private val click: (position: Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val item = ArrayList<Repo?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding : ItemMainBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),R.layout.item_main, parent, false
        )
        return MainViewHolder(binding, click)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        item[position]?.let {
            holder.itemView.ivUser.loadImage(it.owner?.avatarUrl)
            holder.bind(it)
        }
    }

   fun setData(data: ArrayList<Repo>) {
        data.let {
            item.clear()
            item.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int) = item[position]

    class MainViewHolder(private val binding : ItemMainBinding, click: (position: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { click(absoluteAdapterPosition) }
        }
        fun bind(bindingItem: Repo) {
            binding.itemMainVM = bindingItem
        }
    }

}