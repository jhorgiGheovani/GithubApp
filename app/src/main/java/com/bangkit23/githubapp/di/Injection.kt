package com.bangkit23.githubapp.di

import android.content.Context
import com.bangkit23.githubapp.data.RepositoryUser
import com.bangkit23.githubapp.data.local.room.FavoriteUserDatabase
import com.bangkit23.githubapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): RepositoryUser {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao =database.favoriteUserDao()
        return RepositoryUser.getInstance(apiService,dao)
    }
}