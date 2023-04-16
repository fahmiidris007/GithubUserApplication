package com.fahmiproduction.githubuserapplication.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.databinding.ActivityMainBinding
import com.fahmiproduction.githubuserapplication.utils.SettingPref
import com.fahmiproduction.githubuserapplication.viewmodel.RepositoryViewModel
import com.fahmiproduction.githubuserapplication.viewmodel.SettingViewModel
import com.fahmiproduction.githubuserapplication.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private val Context.dataStore by preferencesDataStore(name = SettingPref.PREF_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apply {
            isFailed.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let { fail ->
                    if (fail) {
                        Snackbar.make(
                            window.decorView.rootView,
                            resources.getString(R.string.failed_to_load),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        setNavigationTabs()
        setSettingSummary()
    }

    private fun setNavigationTabs() {
        val navCtrl = findNavController(R.id.bottom_fragment)
        setupActionBarWithNavController(
            navCtrl,
            AppBarConfiguration.Builder(setOf(R.id.home_menu,
                R.id.search_menu,
                R.id.favorite_menu,
                R.id.setting_menu)).build()
        )
        binding.bottom.setupWithNavController(navCtrl)
    }

    private fun setSettingSummary() {
        SETTING_VIEW_MODEL = ViewModelProvider(
            this,
            RepositoryViewModel(SettingPref.getInstance(dataStore))
        )[SettingViewModel::class.java]
        SETTING_VIEW_MODEL!!.getIsDarkMode().observe(this) {
            if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        @Volatile
        private var SETTING_VIEW_MODEL: SettingViewModel? = null
        fun getSettingViewModel(): SettingViewModel? = SETTING_VIEW_MODEL
    }
}