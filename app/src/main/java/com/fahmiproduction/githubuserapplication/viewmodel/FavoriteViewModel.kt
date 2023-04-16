package com.fahmiproduction.githubuserapplication.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fahmiproduction.githubuserapplication.datamodel.FavoriteRepository
import com.fahmiproduction.githubuserapplication.datamodel.FavoriteUser

class FavoriteViewModel(application: Application): ViewModel() {
    private val _favoriteRepository = FavoriteRepository(application)

    fun insertUser(user: FavoriteUser) = _favoriteRepository.insertUser(user)
    fun deleteUser(user: FavoriteUser) = _favoriteRepository.deleteUser(user)
    fun getFavUsers(): LiveData<List<FavoriteUser>> = _favoriteRepository.getFavUsers()
    fun isAFavUser(username: String): LiveData<Int> = _favoriteRepository.isAFavUser(username)
}