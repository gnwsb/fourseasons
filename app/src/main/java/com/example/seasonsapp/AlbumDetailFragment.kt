package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumImage: ImageView = view.findViewById(R.id.album_image)
        val albumName: TextView = view.findViewById(R.id.album_name)
        val artistName: TextView = view.findViewById(R.id.artist_name)

        arguments?.let {
            albumName.text = it.getString("albumName")
            artistName.text = it.getString("artistName")
            val albumImageUrl = it.getString("albumImageUrl")
            Glide.with(this).load(albumImageUrl).into(albumImage)
        }
    }
}
