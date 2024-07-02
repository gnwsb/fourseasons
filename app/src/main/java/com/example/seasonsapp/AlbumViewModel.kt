package com.example.seasonsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Album(val title: String, val artist: String)

class AlbumViewModel : ViewModel() {
    private val _albums = MutableLiveData<List<Album>>(emptyList())
    val albums: LiveData<List<Album>> get() = _albums

    fun addAlbum(title: String, artist: String) {
        if (_albums.value?.size ?: 0 < 6) {
            val newAlbum = Album(title, artist)
            _albums.value = _albums.value?.plus(newAlbum)
        }
    }
}
