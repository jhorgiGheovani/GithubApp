package com.bangkit23.githubapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit23.githubapp.R
import com.bangkit23.githubapp.data.local.room.FavoriteUserEntity
import com.bangkit23.githubapp.databinding.ActivityFavoriteBinding
import com.bangkit23.githubapp.ui.adapter.UserFavoriteAdapter
import com.bangkit23.githubapp.ui.viewmodel.MainViewModel
import com.bangkit23.githubapp.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        mainViewModel.getFavoriteUser().observe(this){users ->
            val data = users.map {
                FavoriteUserEntity(it.username,it.avatarUrl,it.id)
            }

            binding.rvFavorite.adapter = UserFavoriteAdapter(data)

        }


    }
}