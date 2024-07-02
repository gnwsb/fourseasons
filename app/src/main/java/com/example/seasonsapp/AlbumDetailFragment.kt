package com.example.seasonsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("album_prefs", Context.MODE_PRIVATE)
    }

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
        val exitButton: ImageView = view.findViewById(R.id.exit_button)
        val shareButton: ImageView = view.findViewById(R.id.share_button)
        val editButton: ImageView = view.findViewById(R.id.edit_button)

        arguments?.let {
            albumName.text = it.getString("albumName")
            artistName.text = it.getString("artistName")
            val albumImageUrl = it.getString("albumImageUrl")
            Glide.with(this).load(albumImageUrl).into(albumImage)
        }

        exitButton.setOnClickListener {
            findNavController().navigateUp()
        }

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Check out this album: ${albumName.text} by ${artistName.text}")
            }
            startActivity(Intent.createChooser(intent, "Share Album"))
        }

        editButton.setOnClickListener {
            clearAlbumData()
            findNavController().navigateUp()
        }
    }

    private fun clearAlbumData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
