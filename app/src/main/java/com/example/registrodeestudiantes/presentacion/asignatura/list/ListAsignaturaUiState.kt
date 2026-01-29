package com.example.registrodeestudiantes.presentacion.asignatura.list
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura


data class ListAsignaturaUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
