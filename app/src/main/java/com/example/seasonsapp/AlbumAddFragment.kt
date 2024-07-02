package com.example.seasonsapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment

class AlbumAddFragment : Fragment() {

    private lateinit var season: String
    private var albumCount = 0
    private val imageViews = mutableListOf<ImageView>()

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
    }

    private fun showAddAlbumDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("앨범을 추가하세요")

        val input = EditText(requireContext())
        input.hint = "스포티파이 또는 유튜브 뮤직 링크"
        builder.setView(input)

        builder.setPositiveButton("추가") { dialog, _ ->
            val albumLink = input.text.toString().trim()
            if (albumLink.isNotEmpty()) {
                addAlbum(albumLink)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addAlbum(albumLink: String) {
        if (albumCount < imageViews.size) {
            val imageView = imageViews[albumCount]
            imageView.visibility = View.VISIBLE
            // 앨범 이미지를 설정하는 로직 추가
            // 예시로 고정된 이미지 리소스를 사용합니다.
            imageView.setImageResource(R.drawable.plus) // 앨범 이미지를 설정하세요.

            // 다음 앨범 추가 버튼의 위치를 업데이트
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
                        // 나머지 경우도 처리
                    }
                }
            }
            albumCount++
        }
    }
}
