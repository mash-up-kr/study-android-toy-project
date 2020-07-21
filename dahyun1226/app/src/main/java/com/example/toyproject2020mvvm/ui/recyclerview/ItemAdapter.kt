package com.example.toyproject2020mvvm.ui.recyclerview


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject2020mvvm.databinding.RecyclerviewItemBinding
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity
import com.example.toyproject2020mvvm.ui.RepositoryDetailActivity.Companion.EXTRA_FULL_NAME
import com.example.toyproject2020mvvm.viewmodel.MainViewModel


class ItemAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun getItemCount() = viewModel.repoData.value?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, position);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent)
    }

    class ViewHolder(val binding: RecyclerviewItemBinding, parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, RepositoryDetailActivity::class.java)
                intent.putExtra(EXTRA_FULL_NAME, binding.repoData?.fullName)
                it.context.startActivity(intent)
            }
        }

        fun bind(viewModel: MainViewModel, pos: Int) {
            binding.repoData = viewModel.repoData.value?.get(pos)
        }
    }
}
