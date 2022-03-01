package com.supdeweb.androidmusicproject.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.supdeweb.androidmusicproject.ui.home.adapter.album.AlbumFragment
import com.supdeweb.androidmusicproject.ui.home.adapter.track.TrackFragment


private const val NUM_PAGES = 2

class ClassementAdapter(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {
    private val fragments: MutableList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrackFragment()
            1 -> AlbumFragment()
            else -> TrackFragment() //0
        }
    }
}