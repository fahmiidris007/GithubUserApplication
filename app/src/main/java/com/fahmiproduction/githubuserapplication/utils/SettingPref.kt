package com.fahmiproduction.githubuserapplication.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPref private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit { pref -> pref[booleanPreferencesKey(THEME_PREF_NAME)] = isDarkMode }
    }

    fun getIsDarkMode(): Flow<Boolean> {
        return dataStore.data.map { pref -> pref[booleanPreferencesKey(THEME_PREF_NAME)] ?: false }
    }

    companion object {
        const val PREF_NAME = "settings"
        private const val THEME_PREF_NAME = "theme_mode"

        @Volatile
        private var INSTANCE: SettingPref? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPref {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}