package com.example.seasonsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class SeasonNoteFragment : Fragment() {

    private lateinit var season: String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        season = arguments?.getString("season") ?: "spring"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spring_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("seasons_prefs", Context.MODE_PRIVATE)

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val inputEditText = view.findViewById<EditText>(R.id.inputEditText)

        titleTextView.text = "나의 ${getSeasonName(season)}은"

        val savedText = sharedPreferences.getString("${season}_text", "")
        inputEditText.setText(savedText)

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                sharedPreferences.edit().putString("${season}_text", inputEditText.text.toString()).apply()
            }
        }
    }

    private fun getSeasonName(season: String): String {
        return when (season) {
            "spring" -> "봄"
            "summer" -> "여름"
            "autumn" -> "가을"
            "winter" -> "겨울"
            else -> "봄"
        }
    }

    companion object {
        fun newInstance(season: String) = SeasonNoteFragment().apply {
            arguments = Bundle().apply {
                putString("season", season)
            }
        }
    }
}
