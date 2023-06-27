package com.bangkit23.githubapp.ui.viewmodel

import androidx.lifecycle.*
import com.bangkit23.githubapp.data.RepositoryUser
import com.bangkit23.githubapp.data.local.datastore.SettingPreferences
import com.bangkit23.githubapp.data.remote.response.GithubDetailsResponse
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import kotlinx.coroutines.launch

class MainViewModel(private val userRepositoryUser: RepositoryUser ): ViewModel() {

    val searchResult : LiveData<List<ItemsItem>> = userRepositoryUser._searchResult


    fun getUserData(q: String) = userRepositoryUser.userSearch(q)

    fun getDetailUser(username: String) = userRepositoryUser.userDetails(username)

    fun getUserFollowers(username: String) = userRepositoryUser.userFollowers(username)

    fun getUserFollowing(username: String) = userRepositoryUser.userFollowing(username)

    fun getFavoriteUserData(item: GithubDetailsResponse)=userRepositoryUser.userFavorite(item)

    fun deleteFavoriteUser(item: GithubDetailsResponse)=userRepositoryUser.deleteFavoriteUser(item)

    fun getFavoriteUser() = userRepositoryUser.getFavoriteUser()

    fun getFavoriteUserByUsername(username: String) = userRepositoryUser.getFavoriteUserByUsername(username)

    fun getThemeSettings(pref: SettingPreferences): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean, pref: SettingPreferences) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}



