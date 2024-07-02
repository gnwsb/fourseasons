package com.example.seasonsapp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumPagerAdapter(fragment: Fragment, private val season: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2 // 앨범 페이지와 앨범 추가 페이지

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AlbumListFragment.newInstance(season)
            1 -> AlbumAddFragment.newInstance(season)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
