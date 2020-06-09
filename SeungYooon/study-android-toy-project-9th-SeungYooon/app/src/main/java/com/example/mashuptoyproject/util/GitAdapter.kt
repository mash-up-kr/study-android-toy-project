package com.example.mashuptoyproject.util

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mashuptoyproject.CustomProgressDialog
import com.example.mashuptoyproject.DetailActivity
import com.example.mashuptoyproject.R
import com.example.mashuptoyproject.network.model.Item
import kotlinx.android.synthetic.main.list_item.view.*

class GitAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<GitAdapter.ViewHolder>() {

    private val progressDialog = CustomProgressDialog()

    companion object {
        private const val REPO = "repo"
        private const val OWNER = "owner"
        private const val AVATAR_URL = "avatar_url"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        val pos = itemList[position]

        val listener = View.OnClickListener {
            Toast.makeText(it.context, "move to ${item.name}", Toast.LENGTH_SHORT).show()

            holder.itemView.setBackgroundColor(Color.LTGRAY)

            android.os.Handler().postDelayed({
                holder.itemView.setBackgroundColor(Color.WHITE)
            }, 2000)

            val intent = Intent(it.context, DetailActivity::class.java)

            intent.putExtra(AVATAR_URL, pos.owner.avatar_url)
            intent.putExtra(OWNER, pos.owner.login)
            intent.putExtra(REPO, pos.name)

            progressDialog.show(it.context, "Please Wait..")

            android.os.Handler().postDelayed({
                progressDialog.dialog.dismiss()
            }, 2000)

            it.context.startActivity(intent)
        }

        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var view: View = itemView

        fun bind(listener: View.OnClickListener, item: Item) {

            view.txt_login.setText("${item.owner.login}/")
            view.txt_name.setText(item.name)

            if (item.language != null) {
                view.txt_lang.setText(item.language)
            } else {
                view.txt_lang.setText(R.string.no_language)
            }
            Glide.with(itemView.context).load(item.owner.avatar_url).override(1024)
                .into(view.img_avatar)

            view.setOnClickListener(listener)
        }
    }
}