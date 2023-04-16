package com.fahmiproduction.githubuserapplication.datamodel

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val _favoriteDao: FavoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getFavUsers(): LiveData<List<FavoriteUser>> = _favoriteDao.getFavUsers()
    fun insertUser(user: FavoriteUser) = executorService.execute { _favoriteDao.insertFavUser(user) }
    fun deleteUser(user: FavoriteUser) = executorService.execute { _favoriteDao.deleteFavUser(user) }
    fun isAFavUser(username: String): LiveData<Int> = _favoriteDao.isAFavUser(username)
}