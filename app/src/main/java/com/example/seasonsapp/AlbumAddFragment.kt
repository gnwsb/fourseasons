package com.example.seasonsapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class AlbumAddFragment : Fragment() {

    private lateinit var season: String
    private lateinit var spotifyService: SpotifyService
    private lateinit var sharedPreferences: SharedPreferences
    private val REQUEST_CODE_EDIT = 1

    companion object {
        fun newInstance(season: String): AlbumAddFragment {
            val fragment = AlbumAddFragment()
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
        val spotifyInterceptor = SpotifyInterceptor(requireContext())
        spotifyService = SpotifyService.create(spotifyInterceptor)
        sharedPreferences = requireContext().getSharedPreferences("${season}_album_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addAlbumButton = view.findViewById<ImageButton>(R.id.add_album_button)
        val albumImageView = view.findViewById<ImageView>(R.id.album_image)!!

        addAlbumButton.setOnClickListener {
            showAddAlbumDialog()
        }

        loadAlbumData(albumImageView, addAlbumButton)
    }

    private fun showAddAlbumDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("스포티파이 링크를 입력하세요")

        val input = EditText(requireContext())
        input.hint = "스포티파이 링크를 입력하세요"
        builder.setView(input)

        builder.setPositiveButton("추가") { dialog, _ ->
            val albumLink = input.text.toString().trim()
            if (albumLink.isNotEmpty()) {
                addAlbum(albumLink)
            }
            dialog.dismiss()
        }

        builder.show()
    }

    private fun addAlbum(albumLink: String) {
        val albumId = extractAlbumId(albumLink)
        if (albumId != null) {
            fetchAlbumData(albumId, albumLink)
        } else {
            Toast.makeText(requireContext(), "Invalid Spotify link", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAlbumData(albumId: String, albumLink: String) {
        spotifyService.getAlbum(albumId).enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { album ->
                        updateAlbumUI(album, albumLink)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch album data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Failed to fetch album data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateAlbumUI(album: AlbumResponse, albumLink: String) {
        val albumImageView = view?.findViewById<ImageView>(R.id.album_image)!!
        val addAlbumButton = view?.findViewById<ImageButton>(R.id.add_album_button)

        albumImageView.visibility = View.VISIBLE
        Glide.with(this).load(album.images[0].url).into(albumImageView)

        saveAlbumData(album, albumLink)

        albumImageView.setOnClickListener {
            val intent = Intent(requireContext(), AlbumDetailActivity::class.java).apply {
                putExtra("albumName", album.name)
                putExtra("artistName", album.artists.joinToString(", ") { it.name })
                putExtra("albumImageUrl", album.images[0].url)
                putExtra("season", season)
                putExtra("spotifyLink", albumLink)
            }
            startActivityForResult(intent, REQUEST_CODE_EDIT)
            requireActivity().overridePendingTransition(0, 0)
        }

        addAlbumButton?.visibility = View.GONE
    }

    private fun saveAlbumData(album: AlbumResponse, albumLink: String) {
        val editor = sharedPreferences.edit()
        editor.putString("albumId", album.id)
        editor.putString("albumName", album.name)
        editor.putString("artistName", album.artists.joinToString(", ") { it.name })
        editor.putString("albumImageUrl", album.images[0].url)
        editor.putString("spotifyLink", albumLink)
        editor.apply()
    }

    private fun loadAlbumData(albumImageView: ImageView, addAlbumButton: ImageButton) {
        val albumImageUrl = sharedPreferences.getString("albumImageUrl", null)
        if (albumImageUrl != null) {
            albumImageView.visibility = View.VISIBLE
            Glide.with(this).load(albumImageUrl).into(albumImageView)
            addAlbumButton.visibility = View.GONE

            albumImageView.setOnClickListener {
                val intent = Intent(requireContext(), AlbumDetailActivity::class.java).apply {
                    putExtra("albumName", sharedPreferences.getString("albumName", ""))
                    putExtra("artistName", sharedPreferences.getString("artistName", ""))
                    putExtra("albumImageUrl", albumImageUrl)
                    putExtra("season", season)
                    putExtra("spotifyLink", sharedPreferences.getString("spotifyLink", ""))
                }
                startActivityForResult(intent, REQUEST_CODE_EDIT)
                requireActivity().overridePendingTransition(0, 0)
            }
        } else {
            albumImageView.visibility = View.GONE
            addAlbumButton.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK) {
            val albumImageView = view?.findViewById<ImageView>(R.id.album_image)!!
            val addAlbumButton = view?.findViewById<ImageButton>(R.id.add_album_button)!!
            loadAlbumData(albumImageView, addAlbumButton)
        }
    }

    private fun extractAlbumId(albumLink: String): String? {
        val regex = Regex("album/([a-zA-Z0-9]+)")
        val matchResult = regex.find(albumLink)
        return matchResult?.groups?.get(1)?.value
    }
}
