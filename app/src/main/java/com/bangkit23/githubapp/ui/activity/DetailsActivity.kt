package com.bangkit23.githubapp.ui.activity


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bangkit23.githubapp.R
import com.bangkit23.githubapp.data.Result
import com.bangkit23.githubapp.data.remote.response.GithubDetailsResponse
import com.bangkit23.githubapp.databinding.ActivityDetailsBinding
import com.bangkit23.githubapp.ui.adapter.SectionsPagerAdapter
import com.bangkit23.githubapp.ui.viewmodel.MainViewModel
import com.bangkit23.githubapp.ui.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this) //SECTION PAGER ADAPTER

        if (Build.VERSION.SDK_INT >= 33) {
            val username = intent.getStringExtra("username")
            sectionsPagerAdapter.username=username!!
            getUserDetails(username)
        } else {
            @Suppress("DEPRECATION")
            val username = intent.getStringExtra("username") as String
            sectionsPagerAdapter.username=username
            getUserDetails(username)
        }



        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun getUserDetails(username: String){
        mainViewModel.getDetailUser(username).observe(this@DetailsActivity){ result->
            if(result!=null){
                when(result){
                    is Result.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success->{
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        bindingData(data)
                        getFavoriteUserByUsername(data.login!!)
                        binding.fabLikes.apply {
                            setOnClickListener {
                                if(!isFavorite){
                                    addFavoriteUser(data)
                                }else{
                                    deleteFavoriteUser(data)
                                }
                            }
                        }
                    }
                    is Result.Error->{
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailsActivity, "Something went wrong: ${result.error}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun bindingData(data: GithubDetailsResponse){
        binding.username.text = data.login
        binding.name.text = data.name
        Glide.with(this)
            .load("${data.avatarUrl}")
            .into(binding.profilePicture)
        binding.followers.text = resources.getString(R.string.followers, data.followers)
        binding.following.text = resources.getString(R.string.following, data.following)
    }

    private fun addFavoriteUser(data: GithubDetailsResponse){
        mainViewModel.getFavoriteUserData(data).observe(this){ result->
            if(result!=null){
                when(result){
                    is Result.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success->{
                        binding.progressBar.visibility = View.GONE
                        isFavorite=true
                        binding.fabLikes.setImageDrawable(ContextCompat.getDrawable(binding.fabLikes.context, R.drawable.ic_favorite))
                        Toast.makeText(this@DetailsActivity, "Berhasil menambahkan : ${data.login} ke daftar favorite",Toast.LENGTH_SHORT).show()
                    }
                    is Result.Error->{
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailsActivity, "Something went wrong: ${result.error}",Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }

    private fun deleteFavoriteUser(data: GithubDetailsResponse){
        mainViewModel.deleteFavoriteUser(data).observe(this){ result->
            if(result!=null){
                when(result){
                    is Result.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success->{
                        binding.progressBar.visibility = View.GONE
                        isFavorite=false
                        binding.fabLikes.setImageDrawable(ContextCompat.getDrawable(binding.fabLikes.context, R.drawable.ic_favorite_border))
                        Toast.makeText(this@DetailsActivity, "Berhasil Menghapus : ${data.login} dari daftar favorite",Toast.LENGTH_SHORT).show()
                    }
                    is Result.Error->{
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailsActivity, "Something went wrong: ${result.error}",Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }

    private fun getFavoriteUserByUsername(username: String){
        mainViewModel.getFavoriteUserByUsername(username).observe(this){result->
            if (result!=null){
                isFavorite=true
                binding.fabLikes.setImageDrawable(ContextCompat.getDrawable(binding.fabLikes.context, R.drawable.ic_favorite))
            }
        }
    }



    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}