package com.fahmiproduction.githubuserapplication.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fahmiproduction.githubuserapplication.ui.FollowerFragment
import com.fahmiproduction.githubuserapplication.ui.FollowerFragment.Companion.PageType

class FollowerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    lateinit var pageUser: String
    override fun createFragment(position: Int): Fragment {
        return FollowerFragment().apply {
            arguments = Bundle().apply {
                putString(FollowerFragment.ARG_USERNAME, pageUser)
                putString(
                    FollowerFragment.ARG_FOLLOWER_TYPE,
                    PageType.values()[position].name
                )
            }
        }
    }

    override fun getItemCount(): Int = 2
}