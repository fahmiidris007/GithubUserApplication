package com.fahmiproduction.githubuserapplication.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahmiproduction.githubuserapplication.utils.SettingPref

class RepositoryViewModel (
    private val app: Application?,
    private val pref: SettingPref?
) : ViewModelProvider.NewInstanceFactory() {

    constructor(pref: SettingPref) : this(null, pref)
    constructor(app: Application) : this(app, null)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java) && pref != null)
            return SettingViewModel(pref) as T
        else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java) && app != null)
            return FavoriteViewModel(app) as T
        throw IllegalArgumentException("${modelClass.name} is not supported by this factory")
    }
}