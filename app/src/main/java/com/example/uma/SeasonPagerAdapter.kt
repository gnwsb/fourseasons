package com.example.seasonsapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SeasonPagerAdapter(fragment: Fragment, private val season: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactFragment.newInstance(season)
            1 -> AlbumFragment.newInstance(season)
            2 -> SeasonNoteFragment.newInstance(season)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
