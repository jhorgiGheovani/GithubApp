package com.bangkit23.githubapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit23.githubapp.R
import com.bangkit23.githubapp.data.local.datastore.SettingPreferences
import com.bangkit23.githubapp.databinding.ActivitySettingBinding
import com.bangkit23.githubapp.ui.viewmodel.MainViewModel
import com.bangkit23.githubapp.ui.viewmodel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivitySettingBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        mainViewModel.getThemeSettings(pref).observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }


        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            binding.switchTheme.isChecked = isChecked
            mainViewModel.saveThemeSetting(isChecked,pref)
        }

    }
}