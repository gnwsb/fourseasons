package com.example.seasonsapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MusicPlayerFragment : Fragment(), MediaPlayer.OnCompletionListener {

    private lateinit var mediaPlayer: MediaPlayer
    private var albumResId: Int? = null
    private var imageResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumResId = it.getInt("albumResId")
            imageResId = it.getInt("imageResId")
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

        exitButton.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.release()
            findNavController().navigateUp()
        }

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/*"
                putExtra(Intent.EXTRA_STREAM, albumResId?.let { resId ->
                    Uri.parse("android.resource://${requireContext().packageName}/$resId")
                })
            }
            startActivity(Intent.createChooser(intent, "Share Music"))
        }

        albumResId?.let {
            mediaPlayer = MediaPlayer.create(context, it)
            mediaPlayer.setOnCompletionListener(this)
            mediaPlayer.start()

            // 곡 정보 설정
            val songInfo = songInfos[it]
            songTitle.text = songInfo?.title
            songArtist.text = songInfo?.artist
        }

        imageResId?.let {
            albumImage.setImageResource(it)
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        findNavController().navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
