package com.bangkit23.githubapp.data.remote.retrofit

import com.bangkit23.githubapp.data.remote.response.GithubDetailsResponse
import com.bangkit23.githubapp.data.remote.response.GithubResponse
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    suspend fun getUser(
        @Query("q") query: String
    ): GithubResponse
    
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): GithubDetailsResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<ItemsItem>
}