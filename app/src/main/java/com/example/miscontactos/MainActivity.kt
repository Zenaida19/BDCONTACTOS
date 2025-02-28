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
        loadContacts()

        // Acción para agregar un nuevo contacto
        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                // Guardar el contacto en la base de datos
                val newId = dbHelper.addContact(name, phone)
                if (newId != -1L) {
                    Toast.makeText(this, "Contacto registrado", Toast.LENGTH_SHORT).show()
                    loadContacts() // Recargar la lista de contactos
                } else {
                    Toast.makeText(this, "Error al registrar el contacto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese nombre y teléfono", Toast.LENGTH_SHORT).show()
            }
        }
        // Acción para eliminar un contacto
        buttonDelete.setOnClickListener {
            // Aquí eliminamos el primer contacto de la lista como ejemplo
            if (contactList.isNotEmpty()) {
                val contact = contactList[0] // Aquí el índice del contacto que deseas eliminar
                dbHelper.deleteContact(contact.COLUMN_ID)
                loadContacts() // Recargar la lista de contactos después de la eliminación
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No hay contactos para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción para guardar (opcional) - dependiendo de lo que necesites
        buttonSave.setOnClickListener {
            // Aquí se puede agregar la lógica para guardar de alguna manera si es necesario
            // En este ejemplo, guardamos lo mismo que el botón "Registrar"
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val newId = dbHelper.addContact(name, phone)
                if (newId != -1L) {
                    Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show()
                    loadContacts() // Recargar la lista de contactos
                } else {
                    Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese nombre y teléfono", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para cargar los contactos desde la base de datos
    private fun loadContacts() {
        contactList.clear()
        contactList.addAll(dbHelper.getAllContacts())

        // Crear un adaptador para la lista
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList.map { "${it.COLUMN_NAME} - ${it.COLUNM_PHONE}" })
        listView.adapter = adapter
    }

}