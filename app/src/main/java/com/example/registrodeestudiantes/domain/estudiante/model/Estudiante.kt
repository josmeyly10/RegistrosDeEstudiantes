package com.example.registrodeestudiantes.domain.estudiante.model

data class Estudiante(
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int
)