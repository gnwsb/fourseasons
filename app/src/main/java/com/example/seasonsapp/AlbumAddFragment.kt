package com.example.seasonsapp

import android.app.AlertDialog
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
    private var albumCount = 0
    private val imageViews = mutableListOf<ImageView>()
    private lateinit var spotifyService: SpotifyService

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
        setupRetrofit()
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
        addAlbumButton.setOnClickListener {
            showAddAlbumDialog()
        }

        imageViews.add(view.findViewById(R.id.imageView1))
        imageViews.add(view.findViewById(R.id.imageView2))
        imageViews.add(view.findViewById(R.id.imageView3))
        imageViews.add(view.findViewById(R.id.imageView4))
        imageViews.add(view.findViewById(R.id.imageView5))
        imageViews.add(view.findViewById(R.id.imageView6))

        for (i in imageViews.indices) {
            imageViews[i].visibility = if (i < albumCount) View.VISIBLE else View.GONE
        }
    }

    private fun showAddAlbumDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Album")

        val input = EditText(requireContext())
        input.hint = "Enter Spotify link"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val albumLink = input.text.toString().trim()
            if (albumLink.isNotEmpty()) {
                addAlbum(albumLink)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addAlbum(albumLink: String) {
        val albumId = extractAlbumId(albumLink)
        if (albumId != null && albumCount < imageViews.size) {
            fetchAlbumData(albumId)
        } else {
            Toast.makeText(requireContext(), "Invalid Spotify link or maximum albums added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAlbumData(albumId: String) {
        val token = "BQDpw5nmija_CPlBtC-hf52I2L6y_CigWDAtjSLj4i89Nv3TSgcUWB3z8KU4b5fMNU76DmgZ4yrmZDnsWQpBdp7NwtpvlCDGYO-_09cahKGJy8bXQ-jVFU_QKqJiDM73ysWEUxBoYAI5oFyt3053manDEM9PbDZY8I2W2Fxamvl9k_Nf9wKQAVCVfoAyko-KV3DIY-lio_hw4h8RaBztAaMjuF8RvWjbpJb_DBwNNdwCCaBH0kBgIqv94Dd6zckF_wxCsrTmXu6k8DvWvx6-L6ovGmZ0"  // Replace with your actual Spotify token

        spotifyService.getAlbum(albumId, "Bearer $token").enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { album ->
                        updateAlbumUI(album)
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

    private fun updateAlbumUI(album: AlbumResponse) {
        if (albumCount < imageViews.size) {
            val imageView = imageViews[albumCount]
            imageView.visibility = View.VISIBLE
            Glide.with(this).load(album.images[0].url).into(imageView)

            val albumName = album.name
            val artistName = album.artists.joinToString(", ") { it.name }

            // Update add button position
            val nextButton = view?.findViewById<ImageButton>(R.id.add_album_button)
            nextButton?.apply {
                layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
                    when (albumCount) {
                        0 -> {
                            topMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            leftMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                        }
                        1 -> {
                            topMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            rightMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                        }
                        2 -> {
                            topMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            leftMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            bottomMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                        }
                        3 -> {
                            topMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            rightMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                            bottomMargin = resources.getDimensionPixelSize(R.dimen.margin_8dp)
                        }
                    }
                }
            }
            albumCount++
        }
    }

    private fun extractAlbumId(albumLink: String): String? {
        val regex = Regex("album/([a-zA-Z0-9]+)")
        val matchResult = regex.find(albumLink)
        return matchResult?.groups?.get(1)?.value
    }

    private fun setupRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        spotifyService = retrofit.create(SpotifyService::class.java)
    }
}
