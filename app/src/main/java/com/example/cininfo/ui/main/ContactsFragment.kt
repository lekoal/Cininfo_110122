package com.example.cininfo.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.cininfo.R
import com.example.cininfo.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getContacts()
            } else {
                Toast.makeText(
                    context,
                    R.string.need_permission_to_read_contacts,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    @SuppressLint("Range")
    private fun getContacts() {
        context?.let { nonNullContext ->
            val cr = nonNullContext.contentResolver
            val cursorWithContacts: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts._ID + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                        if (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                                .toInt() > 0
                        ) {
                            val cursorWithPhones: Cursor? = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                null,
                                null,
                                ContactsContract.Contacts._ID + " ASC"
                            )

                            cursorWithPhones?.let { phoneCursor ->
                                if (phoneCursor.moveToPosition(i)) {

                                    val phoneNumber =
                                        phoneCursor.getString(
                                            phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                        )
                                    addView(name, phoneNumber)
                                }
                            }
                            cursorWithPhones?.close()
                        }
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun addView(name: String?, phoneNumber: String?) = with(binding) {
        contactItem.addView(TextView(requireContext()).apply {
            text = "${name}, \n${phoneNumber}"
            textSize = resources.getDimension(R.dimen.contacts_name_size)
            setOnClickListener {
                Toast.makeText(context, "PRESS", Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}