package com.example.seasonsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyService {
    @GET("albums/{id}")
    fun getAlbum(
        @Path("id") albumId: String,
        @Header("Authorization") authToken: String
    ): Call<AlbumResponse>
}
