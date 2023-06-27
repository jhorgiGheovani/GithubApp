package com.bangkit23.githubapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit23.githubapp.R
import com.bangkit23.githubapp.data.Result
import com.bangkit23.githubapp.data.local.datastore.SettingPreferences
import com.bangkit23.githubapp.data.remote.response.ItemsItem
import com.bangkit23.githubapp.databinding.ActivityMainBinding
import com.bangkit23.githubapp.ui.adapter.MainAdapter
import com.bangkit23.githubapp.ui.viewmodel.MainViewModel
import com.bangkit23.githubapp.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        binding.goSearchTV.visibility= View.VISIBLE


        mainViewModel.searchResult.observe(this) {
            binding.goSearchTV.visibility= View.GONE
            if(it.isEmpty()){
                binding.resultEmpty.visibility =View.VISIBLE
            }

            setUserData(it)
        }

        //Inisialisasi preference
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel.getThemeSettings(pref).observe(this@MainActivity) { isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val favoriteView = menu.findItem(R.id.favorite)
        val settingView = menu.findItem(R.id.setting)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            //Gunakan method ini ketika search selesai atau OK
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getUserData(query).observe(this@MainActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.goSearchTV.visibility = View.GONE

                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.goSearchTV.visibility = View.GONE
                                val userData = result.data

                                if (userData.isEmpty()) {
                                    binding.resultEmpty.visibility = View.VISIBLE
                                } else {
                                    binding.resultEmpty.visibility = View.GONE
                                    setUserData(userData)
                                }

                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.goSearchTV.visibility = View.VISIBLE
                                Toast.makeText(
                                    this@MainActivity,
                                    "Something went wrong: ${result.error}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                searchView.clearFocus()
                return true
            }

            //Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


        favoriteView.setOnMenuItemClickListener(this)
        settingView.setOnMenuItemClickListener(this)
        return true
    }


    //Fungsi untuk get data from api and pass it to adapter
    private fun setUserData(userSearch: List<ItemsItem>) {
        val getUserData = userSearch.map {
            ItemsItem(it.login, it.id)
        }


        //Kirim data ke adapter
        val adapter = MainAdapter(getUserData)

        //bind data kedalam view recycler View
        binding.rvUser.adapter = adapter

    }

    override fun onMenuItemClick(p0: MenuItem): Boolean {
        return when (p0.itemId) {
            R.id.favorite -> {
                val favorite = Intent(this, FavoriteActivity::class.java)
                startActivity(favorite)
                true
            }
            R.id.setting -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
                true
            }
            else -> false
        }
    }


}