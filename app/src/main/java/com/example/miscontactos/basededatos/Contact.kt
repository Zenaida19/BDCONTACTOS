package com.example.miscontactos.basededatos

data class Contact(
    val COLUMN_ID: Long,      // El id único del contacto
    val COLUMN_NAME: String,  // El nombre del contacto
    val COLUNM_PHONE: String  // El número de teléfono del contacto
)
