package com.bangkit23.githubapp.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit23.githubapp.ui.fragment.FollowingFollowersFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount(): Int {
        return 2 //jumlah tab
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingFollowersFragment()

        fragment.arguments = Bundle().apply {
            putInt(FollowingFollowersFragment.ARG_POSITION, position+1)
            putString(FollowingFollowersFragment.ARG_USERNAME, username)

        }
        return fragment

    }

}