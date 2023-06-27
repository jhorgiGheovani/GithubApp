package com.bangkit23.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import com.bangkit23.githubapp.databinding.UserItemBinding
import com.bangkit23.githubapp.ui.activity.DetailsActivity
import com.bumptech.glide.Glide

class MainAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(name,id) = listUser[position]

        holder.binding.tvItemName.text = name

        Glide.with(holder.itemView.context)
            .load("https://avatars.githubusercontent.com/u/${id}?v=4")
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailsActivity::class.java)
            intentDetail.putExtra("username", listUser[position].login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listUser.size
}