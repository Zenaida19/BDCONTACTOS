package com.example.miscontactos

import android.os.Bundle
import android.provider.ContactsContract.DeletedContacts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: editTextText
    private lateinit var editTextPhone: EditTextText2
    private lateinit var listView: ListView
    private lateinit var buttonRegister: Button
    private lateinit var buttonSave: Button2
    private lateinit var buttonDeletedContacts: DeletedContacts: Button3
    private lateinit var dbHelper: DBHelper
    private lateinit var contactList: MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}