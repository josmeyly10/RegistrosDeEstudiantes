package com.example.registrodeestudiantes.presentacion.estudiante.list

import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante

data class ListEstudianteUiState(
    val estudiantes: List<Estudiante> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

