package com.example.seasonsapp

data class AlbumResponse(
    val id: String,
    val name: String,
    val images: List<Image>,
    val artists: List<Artist>
)

data class Image(
    val url: String,
    val height: Int,
    val width: Int
)

data class Artist(
    val name: String
)
