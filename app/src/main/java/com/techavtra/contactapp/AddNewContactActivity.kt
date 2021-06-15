package com.techavtra.contactapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_new_contact.*


class AddNewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)
        init()

    }

    private fun init() {
        btnSave.setOnClickListener {

            if (validation()) {
                val contactIntent = Intent(ContactsContract.Intents.Insert.ACTION)
                contactIntent.type = ContactsContract.RawContacts.CONTENT_TYPE

                contactIntent
                    .putExtra(ContactsContract.Intents.Insert.NAME, edtName.text.toString())
                    .putExtra(ContactsContract.Intents.Insert.PHONE, edtNumber.text.toString())

                startActivityForResult(contactIntent, 1)
            }
        }
    }

    private fun validation(): Boolean {
        if (edtName.text.toString().length < 0) {
            edtName.error = "Please enter name"
            return false
        } else if (edtNumber.text.toString().length < 0) {
            edtNumber.error = "Please enter Number"
            return false
        }

        return true

    }
}