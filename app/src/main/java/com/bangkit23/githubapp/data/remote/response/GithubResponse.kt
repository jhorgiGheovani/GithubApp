package com.bangkit23.githubapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
    @field:SerializedName("items")
    val items: List<ItemsItem>
)

data class ItemsItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    val avatar_url: String? = null,
)