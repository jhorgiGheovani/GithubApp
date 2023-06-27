package com.bangkit23.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(likes: FavoriteUserEntity)


    @Update
    suspend fun update(likes: FavoriteUserEntity)

    @Delete
    suspend fun delete(likes: FavoriteUserEntity)

    //Select all user from FavoriteUser table when username same with current username
    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

    @Query("SELECT * FROM favorite")
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>
}