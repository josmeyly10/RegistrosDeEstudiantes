package com.example.registrodeestudiantes.presentacion.navigation
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()

    @Serializable
    data object AsignaturaList : Screen()
}