package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AlbumAddFragment : Fragment() {

    private lateinit var season: String

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumLinkEditText = view.findViewById<EditText>(R.id.album_link_edit_text)
        val addAlbumButton = view.findViewById<Button>(R.id.add_album_button)

        addAlbumButton.setOnClickListener {
            val albumLink = albumLinkEditText.text.toString().trim()
            if (albumLink.isNotEmpty()) {
                // 앨범 추가 로직 구현
                addAlbum(albumLink)
            }
        }
    }

    private fun addAlbum(albumLink: String) {
        // 앨범 추가 로직을 구현하세요.
        // 링크에서 앨범 표지 및 정보 가져오기, 저장, UI 업데이트 등의 작업을 수행합니다.
    }
}
