package com.bangkit23.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.githubapp.data.local.room.FavoriteUserEntity
import com.bangkit23.githubapp.databinding.UserItemBinding
import com.bangkit23.githubapp.ui.activity.DetailsActivity
import com.bumptech.glide.Glide

class UserFavoriteAdapter(private val listUser: List<FavoriteUserEntity>): RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>()  {

    class ViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteUserEntity = listUser[position]
        holder.binding.tvItemName.text = favoriteUserEntity.username

        Glide.with(holder.itemView.context)
            .load(favoriteUserEntity.avatarUrl)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailsActivity::class.java)
            intentDetail.putExtra("username", listUser[position].username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }


    override fun getItemCount(): Int  = listUser.size



}