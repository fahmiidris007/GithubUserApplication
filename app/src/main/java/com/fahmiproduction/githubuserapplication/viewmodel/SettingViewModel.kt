package com.fahmiproduction.githubuserapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fahmiproduction.githubuserapplication.utils.SettingPref
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPref) : ViewModel() {
    fun saveThemeMode(isDarkMode: Boolean) = viewModelScope.launch { pref.saveThemeMode(isDarkMode) }
    fun getIsDarkMode(): LiveData<Boolean> = pref.getIsDarkMode().asLiveData()
}