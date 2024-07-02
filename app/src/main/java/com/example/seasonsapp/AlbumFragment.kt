package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class AlbumFragment : Fragment() {

    private lateinit var season: String

    companion object {
        fun newInstance(season: String): AlbumFragment {
            val fragment = AlbumFragment()
            val args = Bundle()
            args.putString("season", season)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            season = it.getString("season") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.album_view_pager)
        val indicator = view.findViewById<CircleIndicator3>(R.id.indicator)

        viewPager.adapter = AlbumPagerAdapter(this, season)
        indicator.setViewPager(viewPager)
    }
}
