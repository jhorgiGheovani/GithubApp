package com.bangkit23.githubapp.data

import android.util.Log
import androidx.lifecycle.*
import com.bangkit23.githubapp.data.local.room.FavoriteUserDao
import com.bangkit23.githubapp.data.local.room.FavoriteUserEntity
import com.bangkit23.githubapp.data.remote.response.GithubDetailsResponse
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import com.bangkit23.githubapp.data.remote.retrofit.ApiService

class RepositoryUser private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
    ) {
    val _searchResult  = MutableLiveData<List<ItemsItem>>()


    fun userSearch(query: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(query).items
            val userList = response.map { data->
                ItemsItem(
                    data.login,
                    data.id,
                    data.avatar_url
                )
            }
            _searchResult.value=userList
            emit(Result.Success(userList))
        }catch (e: Exception){
            Log.d("userSearch", "userSearch: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun userDetails(username: String): LiveData<Result<GithubDetailsResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        }catch (e: Exception){
            Log.d("userDetailsRespository", "${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun userFollowers(query: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(query)
            val userList = response.map { data->
                ItemsItem(
                    data.login,
                    data.id,
                    data.avatar_url
                )
            }
            emit(Result.Success(userList))
        }catch (e: Exception){
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun userFollowing(query: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(query)
            val userList = response.map { data->
                ItemsItem(
                    data.login,
                    data.id,
                    data.avatar_url
                )
            }
            emit(Result.Success(userList))
        }catch (e: Exception){
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun userFavorite(data: GithubDetailsResponse): LiveData<Result<FavoriteUserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val result = FavoriteUserEntity(data.login!!,data.avatarUrl,data.id!!)
            favoriteUserDao.insert(result)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteFavoriteUser(data: GithubDetailsResponse): LiveData<Result<FavoriteUserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val result = FavoriteUserEntity(data.login!!,data.avatarUrl,data.id!!)
            favoriteUserDao.delete(result)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>{
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>{
        return favoriteUserDao.getFavoriteUser()
    }


    companion object {
        @Volatile
        private var instance: RepositoryUser? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): RepositoryUser =
            instance ?: synchronized(this) {
                instance ?: RepositoryUser(apiService, favoriteUserDao)
            }.also { instance = it }
    }

}


