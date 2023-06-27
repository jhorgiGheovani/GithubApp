package com.bangkit23.githubapp.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avatar")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "id")
    var id: Int,
)
