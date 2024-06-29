package com.example.seasonsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ContactFragment : Fragment() {

    private lateinit var season: String

    companion object {
        fun newInstance(season: String): ContactFragment {
            val fragment = ContactFragment()
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
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED) {
            // 연락처 접근 코드
            loadContacts()
        } else {
            // 권한이 없을 때의 처리
        }
    }

    private fun loadContacts() {
        // 실제 연락처를 로드하는 코드
    }
}
