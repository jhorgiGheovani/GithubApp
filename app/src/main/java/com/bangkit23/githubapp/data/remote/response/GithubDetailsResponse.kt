package com.bangkit23.githubapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubDetailsResponse(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,
)