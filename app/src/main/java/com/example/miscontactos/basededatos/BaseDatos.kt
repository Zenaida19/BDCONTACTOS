package com.example.miscontactos.basededatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper

// Definimos la clase BaseDatos que extiende SQLiteOpenHelper
class BaseDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Definimos las constantes para el nombre de la base de datos, la versión y las columnas
    companion object {
        const val DATABASE_NAME = "contacts.db"  // Nombre de la base de datos
        const val DATABASE_VERSION = 1  // Versión inicial de la base de datos
        const val TABLE_NAME = "contacts"  // Nombre de la tabla de contactos
        const val COLUMN_ID = "id"  // Columna para el ID
        const val COLUMN_NAME = "name"  // Columna para el nombre
        const val COLUMN_PHONE = "phone"  // Columna para el teléfono
    }

    // Este método se llama cuando se crea la base de datos por primera vez
    override fun onCreate(db: SQLiteDatabase?) {
        // Sentencia SQL para crear la tabla
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT
            );
        """
        db?.execSQL(createTableQuery)  // Ejecutamos la creación de la tabla
    }

    // Este método se llama si la base de datos se actualiza (por ejemplo, cambiar la versión)
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Si la base de datos cambia, eliminamos la tabla antigua y la creamos de nuevo
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    // Función para agregar un contacto
    fun addContact(name: String, phone: String): Long {
        val db = writableDatabase  // Obtenemos una instancia de la base de datos en modo escritura
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)  // Asignamos el nombre
            put(COLUMN_PHONE, phone)  // Asignamos el teléfono
        }
        // Insertamos los valores en la tabla y devolvemos el ID del contacto agregado
        return db.insert(TABLE_NAME, null, values)
    }

    // Función para obtener todos los contactos
    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()  // Lista para almacenar los contactos
        val db = readableDatabase  // Obtenemos una instancia de la base de datos en modo lectura
        val cursor: Cursor = db.query(
            TABLE_NAME, null, null, null, null, null, null
        )

        if (cursor.moveToFirst()) {  // Si el cursor tiene registros
            do {
                // Obtenemos los datos de cada columna del contacto
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                // Agregamos el contacto a la lista
                contacts.add(Contact(id, name, phone))
            } while (cursor.moveToNext())  // Iteramos a través de todos los registros
        }
        cursor.close()  // Cerramos el cursor
        return contacts  // Devolvemos la lista de contactos
    }

    // Función para eliminar un contacto
    fun deleteContact(contactId: Long): Int {
        val db = writableDatabase  // Obtenemos una instancia de la base de datos en modo escritura
        // Eliminamos el contacto que coincida con el ID
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(contactId.toString()))
    }
}
