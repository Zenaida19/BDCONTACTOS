package com.example.miscontactos

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miscontactos.basededatos.BaseDatos
import com.example.miscontactos.MainActivity.C
import com.example.miscontactos.basededatos.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var listView: ListView
    private lateinit var buttonRegister: Button
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private lateinit var dbHelper: BaseDatos
    private lateinit var contactList: MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos los componentes de la UI
        editTextName = findViewById(R.id.editTextText) // Nombre
        editTextPhone = findViewById(R.id.editTextText2) // Número
        listView = findViewById(R.id.listView)
        buttonRegister = findViewById(R.id.button) // Registrar
        buttonSave = findViewById(R.id.button2) // Guardar
        buttonDelete = findViewById(R.id.button3) // Eliminar

        dbHelper = BaseDatos(this)
        contactList = mutableListOf()

        // Cargar los contactos cuando se inicia la aplicación
        Contact()
    }
}