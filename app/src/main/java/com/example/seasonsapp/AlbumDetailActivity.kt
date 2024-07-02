package com.example.seasonsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spotifyLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_album_detail)

        val season = intent.getStringExtra("season")
        sharedPreferences = getSharedPreferences("${season}_album_prefs", Context.MODE_PRIVATE)

        val albumImage: ImageView = findViewById(R.id.album_image)
        val albumName: TextView = findViewById(R.id.album_name)
        val artistName: TextView = findViewById(R.id.artist_name)
        val exitButton: ImageView = findViewById(R.id.exit_button)
        val shareButton: ImageView = findViewById(R.id.share_button)
        val editButton: ImageView = findViewById(R.id.edit_button)

        val name = intent.getStringExtra("albumName")
        val artist = intent.getStringExtra("artistName")
        val imageUrl = intent.getStringExtra("albumImageUrl")
        spotifyLink = intent.getStringExtra("spotifyLink") ?: ""

        albumName.text = name
        artistName.text = artist
        Glide.with(this).load(imageUrl).into(albumImage)

        val rootView = findViewById<View>(R.id.music_player_root)
        val backgroundDrawable = when (season?.lowercase()) {
            "spring" -> R.drawable.sprb
            "summer" -> R.drawable.sumb
            "autumn" -> R.drawable.autb
            "winter" -> R.drawable.winb
            else -> R.drawable.sprb
        }
        rootView.setBackgroundResource(backgroundDrawable)

        exitButton.setOnClickListener {
            finishWithoutAnimation()
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Check out this album: $spotifyLink")
            }
            startActivity(Intent.createChooser(shareIntent, "Share Album"))
        }

        editButton.setOnClickListener {
            clearAlbumData()
            setResult(RESULT_OK)
            finishWithoutAnimation()
        }
    }

    private fun clearAlbumData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun finishWithoutAnimation() {
        finish()
        overridePendingTransition(0, 0)
    }
}
