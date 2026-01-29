package com.example.registrodeestudiantes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignaturas")
data class AsignaturaEntity(
    @PrimaryKey(autoGenerate = true)
    val asignaturaId: Int = 0,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: Int = 0
)