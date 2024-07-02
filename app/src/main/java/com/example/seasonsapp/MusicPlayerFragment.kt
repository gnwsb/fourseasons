package com.example.seasonsapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MusicPlayerFragment : Fragment() {

    private var albumResId: Int? = null
    private var imageResId: Int? = null
    private var season: String? = null
    private var songInfo: SongInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumResId = it.getInt("albumResId")
            imageResId = it.getInt("imageResId")
            season = it.getString("season")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exitButton: ImageView = view.findViewById(R.id.exit_button)
        val shareButton: ImageView = view.findViewById(R.id.share_button)
        val albumImage: ImageView = view.findViewById(R.id.album_image)
        val songTitle: TextView = view.findViewById(R.id.song_title)
        val songArtist: TextView = view.findViewById(R.id.song_artist)

        // Set background image
        val rootView: View = view.findViewById(R.id.music_player_root)
        season?.let {
            val backgroundDrawable = when (it.lowercase()) {
                "spring" -> R.drawable.sprb
                "summer" -> R.drawable.sumb
                "autumn" -> R.drawable.autb
                "winter" -> R.drawable.winb
                else -> R.drawable.sprb
            }
            rootView.setBackgroundResource(backgroundDrawable)
        }

        exitButton.setOnClickListener {
            findNavController().navigateUp()
        }

        shareButton.setOnClickListener {
            songInfo?.let { info ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, info.link)
                }
                startActivity(Intent.createChooser(intent, "Share Link"))
            }
        }

        albumResId?.let {
            // Set song information
            songInfo = songInfos[it]
            songTitle.text = songInfo?.title
            songArtist.text = songInfo?.artist
        }

        imageResId?.let {
            albumImage.setImageResource(it)
        }
    }
}
