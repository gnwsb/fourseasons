package com.example.seasonsapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment

class EmptyFragment : Fragment() {

    private lateinit var sentenceContainer: LinearLayout
    private lateinit var inputSentence: EditText
    private lateinit var addSentenceButton: Button

    private val sentencesKey = "SENTENCES"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sentenceContainer = view.findViewById(R.id.sentenceContainer)
        inputSentence = view.findViewById(R.id.inputSentence)
        addSentenceButton = view.findViewById(R.id.addSentenceButton)

        loadSentences()

        addSentenceButton.setOnClickListener {
            val sentence = inputSentence.text.toString()
            if (sentence.isNotBlank() && sentence.length <= 17) {
                addSentenceView(sentence)
                saveSentence(sentence)
                inputSentence.text.clear()
            }
        }
    }

    private fun addSentenceView(sentence: String) {
        val textView = TextView(context).apply {
            text = sentence
            textSize = 18f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16
            }
        }
        sentenceContainer.addView(textView)
    }

    private fun saveSentence(sentence: String) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val sentences = sharedPreferences.getStringSet(sentencesKey, mutableSetOf()) ?: mutableSetOf()
        sentences.add(sentence)
        sharedPreferences.edit {
            putStringSet(sentencesKey, sentences)
        }
    }

    private fun loadSentences() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val sentences = sharedPreferences.getStringSet(sentencesKey, mutableSetOf()) ?: mutableSetOf()
        for (sentence in sentences) {
            addSentenceView(sentence)
        }
    }
}
