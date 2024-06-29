package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // 계절에 맞는 이미지 및 노래 목록 설정
        val images = getImagesForSeason(season)
        val songs = getSongsForSeason(season)
        val adapter = AlbumAdapter(images, songs)
        recyclerView.adapter = adapter

        return view
    }

    private fun getImagesForSeason(season: String): List<Int> {
        return when (season.lowercase()) {
            "spring" -> listOf(R.drawable.spr1, R.drawable.spr2, R.drawable.spr3, R.drawable.spr4, R.drawable.spr5, R.drawable.spr6)
            "summer" -> listOf(R.drawable.sum1, R.drawable.sum2, R.drawable.sum3, R.drawable.sum4, R.drawable.sum5, R.drawable.sum6)
            "autumn" -> listOf(R.drawable.aut1, R.drawable.aut2, R.drawable.aut3, R.drawable.aut4, R.drawable.aut5, R.drawable.aut6)
            "winter" -> listOf(R.drawable.win1, R.drawable.win2, R.drawable.win3, R.drawable.win4, R.drawable.win5, R.drawable.win6)
            else -> emptyList()
        }
    }

    private fun getSongsForSeason(season: String): List<Int> {
        return when (season.lowercase()) {
            "spring" -> listOf(R.raw.spr1, R.raw.spr2, R.raw.spr3, R.raw.spr4, R.raw.spr5, R.raw.spr6)
            "summer" -> listOf(R.raw.sum1, R.raw.sum2, R.raw.sum3, R.raw.sum4, R.raw.sum5, R.raw.sum6)
            "autumn" -> listOf(R.raw.aut1, R.raw.aut2, R.raw.aut3, R.raw.aut4, R.raw.aut5, R.raw.aut6)
            "winter" -> listOf(R.raw.win1, R.raw.win2, R.raw.win3, R.raw.win4, R.raw.win5, R.raw.win6)
            else -> emptyList()
        }
    }
}
