package com.fahmiproduction.githubuserapplication.datamodel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(user: FavoriteUser)

    @Delete
    fun deleteFavUser(user: FavoriteUser)

    @Query("SELECT COUNT (username) from favorite_user WHERE username = :username")
    fun isAFavUser(username: String): LiveData<Int>

    @Query("SELECT * from favorite_user")
    fun getFavUsers(): LiveData<List<FavoriteUser>>
}