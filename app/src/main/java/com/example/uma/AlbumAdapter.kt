package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController

class AlbumAdapter(private val images: List<Int>, private val songs: List<Int>, private val season: String) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val imageResId = images[position]
        val songResId = songs[position]
        holder.imageView.setImageResource(imageResId)
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("albumResId", songResId)
                putInt("imageResId", imageResId)
                putString("season", season)
            }
            it.findNavController().navigate(R.id.musicPlayerFragment, bundle)
        }
    }

    override fun getItemCount() = images.size
}
