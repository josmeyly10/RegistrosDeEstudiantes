package com.example.registrodeestudiantes.presentacion.estudiante.list
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante

sealed interface ListEstudianteUiEvent {
    data class SearchQueryChanged(val query: String) : ListEstudianteUiEvent
    data class OnEstudianteClick(val id: Int) : ListEstudianteUiEvent
    data object OnAddClick : ListEstudianteUiEvent
    data class OnDeleteEstudiante(val id: Int) : ListEstudianteUiEvent
    data class OnSaveEstudiante(val estudiante: Estudiante) : ListEstudianteUiEvent
}