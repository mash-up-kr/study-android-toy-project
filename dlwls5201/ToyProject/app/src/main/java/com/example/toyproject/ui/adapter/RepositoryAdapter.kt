package com.example.toyproject.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyproject.R
import com.example.toyproject.ui.model.RepoItem
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private var items: MutableList<RepoItem> = mutableListOf()

    private val placeholder = ColorDrawable(Color.GRAY)

    var onItemClick: ((repoItem: RepoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepositoryHolder(parent).apply {
            itemView.setOnClickListener {
                val item = items[adapterPosition]
                onItemClick?.invoke(item)
            }
        }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        items[position].let { repo ->
            with(holder.itemView) {
                Glide.with(context)
                    .load(repo.owner.ownerUrl)
                    .placeholder(placeholder)
                    .into(ivProfile)

                tvTitle.text = repo.title
                tvLanguage.text = if (TextUtils.isEmpty(repo.language))
                    context.getText(R.string.no_language_specified)
                else
                    repo.language
            }
        }
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<RepoItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    class RepositoryHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
    )
}