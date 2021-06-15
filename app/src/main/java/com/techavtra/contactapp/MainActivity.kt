package com.techavtra.contactapp

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.techavtra.contactapp.adapter.ContactListAdapter
import com.techavtra.contactapp.model.ContactClass
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var contactList:  ArrayList<ContactClass>
    private lateinit var contactListAdapter: ContactListAdapter


    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerContact.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        loadContacts()
        clickListener()

    }

    private fun clickListener()
    {
        floating_action_button.setOnClickListener {
            val intent = Intent(this, AddNewContactActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadContacts() {

        contactList=ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {

            contactList = getContacts()
            contactListAdapter=ContactListAdapter(contactList)
            recyclerContact.adapter=contactListAdapter
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }

    private fun getContacts(): ArrayList<ContactClass> {

        progressBar1.visibility= View.VISIBLE
        val contactarrayList=ArrayList<ContactClass>()

        //val builder = StringBuilder()
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)
        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))


                                    var contactClass = ContactClass(name,phoneNumValue)
                                    contactarrayList.add(contactClass)

                                }
                            }
                        }
                        if (cursorPhone != null) {
                            cursorPhone.close()
                        }
                    }
                }


            } else {
                progressBar1.visibility= View.GONE

                //   toast("No contacts available!")
            }
        }
        if (cursor != null) {
            cursor.close()
            progressBar1.visibility= View.GONE

        }
        return contactarrayList
    }
}