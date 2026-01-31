package com.example.registrodeestudiantes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TiposPenalidades")
data class TipoPenalidadEntity(
    @PrimaryKey(autoGenerate = true)
    val tipoId: Int = 0,
    val nombre: String,
    val descripcion: String,
    val puntosDescuento: Int
)