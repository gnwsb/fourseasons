package com.example.seasonsapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random
import android.util.Log

class ContactFragment : Fragment() {

    private lateinit var season: String
    private val REQUEST_CODE_READ_CONTACTS = 100

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

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALL_LOG)
            == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED) {
            // 통화 기록 접근 코드
            loadContacts()
        } else {
            // 권한 요청
            requestPermissions(arrayOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS), REQUEST_CODE_READ_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })) {
                // 권한이 부여되면 통화 기록을 로드
                loadContacts()
            } else {
                // 권한이 거부되었을 때 처리
                Toast.makeText(requireContext(), "통화 기록 및 연락처 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadContacts() {
        val contact1TextView = view?.findViewById<TextView>(R.id.contact_1)
        val contact2TextView = view?.findViewById<TextView>(R.id.contact_2)
        val contact3TextView = view?.findViewById<TextView>(R.id.contact_3)

        val callLogs = getCallLogs()
        if (callLogs.isNotEmpty()) {
            val sortedContacts = callLogs.entries.sortedByDescending { it.value }.take(3)
            val contactViews = listOf(contact1TextView, contact2TextView, contact3TextView)
            for ((index, entry) in sortedContacts.withIndex()) {
                val (number, _) = entry
                val contactName = getContactName(number) ?: "Unknown"
                contactViews[index]?.text = "$contactName\n$number"
                contactViews[index]?.setOnClickListener {
                    sendSmsWithSeasonPoem(number)
                }
            }
        } else {
            val randomContacts = getRandomContacts()
            val contactViews = listOf(contact1TextView, contact2TextView, contact3TextView)
            for ((index, contact) in randomContacts.withIndex()) {
                val (name, number) = contact
                contactViews[index]?.text = "$name\n$number"
                contactViews[index]?.setOnClickListener {
                    sendSmsWithSeasonPoem(number)
                }
            }
        }
    }

    private fun getCallLogs(): Map<String, Int> {
        val projection = arrayOf(CallLog.Calls.NUMBER, CallLog.Calls.DATE)
        val cursor: Cursor? = requireActivity().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        val contactInteractions = mutableMapOf<String, Int>()
        cursor?.use {
            while (it.moveToNext()) {
                val number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                val date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))

                if (isDateInSeason(date)) {
                    contactInteractions[number] = contactInteractions.getOrDefault(number, 0) + 1
                }
            }
        }
        return contactInteractions
    }

    private fun isDateInSeason(date: Long): Boolean {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = date
        val month = calendar.get(java.util.Calendar.MONTH) + 1

        return when (season) {
            "spring" -> month in 3..5
            "summer" -> month in 6..8
            "autumn" -> month in 9..11
            "winter" -> month == 12 || month in 1..2
            else -> false
        }
    }

    private fun getContactName(phoneNumber: String): String? {
        val uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
        val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)
        val cursor: Cursor? = requireActivity().contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME))
            }
        }
        return null
    }

    private fun getRandomContacts(): List<Pair<String, String>> {
        val projection = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)
        val cursor: Cursor? = requireActivity().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        val contacts = mutableListOf<Pair<String, String>>()
        cursor?.use {
            while (it.moveToNext()) {
                val contactId = it.getLong(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = getPhoneNumber(contactId)

                if (phoneNumber != null) {
                    contacts.add(Pair(name, phoneNumber))
                }
            }
        }

        contacts.shuffle()
        return contacts.take(3)
    }

    private fun getPhoneNumber(contactId: Long): String? {
        val cursor: Cursor? = requireActivity().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        return null
    }

    private fun sendSmsWithSeasonPoem(phoneNumber: String) {
        val poem = getRandomPoemForSeason()
        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", poem)
        }
        startActivity(smsIntent)
    }

    private fun getRandomPoemForSeason(): String {
        val assetManager = requireContext().assets

        // 디버깅: assets 폴더 내 파일 목록 출력
        try {
            val assetFiles = assetManager.list("")
            Log.d("ContactFragment", "Assets directory contains: ${assetFiles?.joinToString()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ContactFragment", "Error listing assets", e)
        }

        val files = when (season) {
            "spring" -> arrayOf("spp1.txt", "spp2.txt", "spp3.txt", "spp4.txt", "spp5.txt", "spp6.txt")
            "summer" -> arrayOf("sup1.txt", "sup2.txt", "sup3.txt", "sup4.txt", "sup5.txt", "sup6.txt")
            "autumn" -> arrayOf("ap1.txt", "ap2.txt", "ap3.txt", "ap4.txt", "ap5.txt", "ap6.txt")
            "winter" -> arrayOf("wp1.txt", "wp2.txt", "wp3.txt", "wp4.txt", "wp5.txt", "wp6.txt", "wp7.txt")
            else -> arrayOf()
        }

        if (files.isEmpty()) {
            Log.e("ContactFragment", "No files found for season: $season")
            return "계절에 맞는 시를 찾을 수 없습니다."
        }

        val randomFile = files.random()
        return try {
            val inputStream = assetManager.open(randomFile)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val poem = bufferedReader.use { it.readText() }
            Log.d("ContactFragment", "Successfully read from file: $randomFile")
            poem
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ContactFragment", "Error reading file: $randomFile", e)
            "계절에 맞는 시를 찾을 수 없습니다. 파일 이름: $randomFile"
        }
    }


}
