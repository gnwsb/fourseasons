package com.example.seasonsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.provider.ContactsContract
import android.database.Cursor
import android.widget.LinearLayout
import android.widget.TextView

class ContactFragment : Fragment() {

    companion object {
        fun newInstance(season: String) = ContactFragment().apply {
            arguments = Bundle().apply {
                putString("SEASON", season)
            }
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

        val contactsLayout = view.findViewById<LinearLayout>(R.id.contacts_layout)

        val cursor: Cursor? = context?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )

        cursor?.let {
            var count = 0
            while (it.moveToNext() && count < 3) {
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contactView = TextView(context)
                contactView.text = "$name | $number"
                contactsLayout.addView(contactView)
                count++
            }
            it.close()
        }
    }
}
