package com.example.registrodeestudiantes.presentacion.asignatura.list
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura

sealed interface ListAsignaturaUiEvent {

    data class SearchQueryChanged(val query: String) : ListAsignaturaUiEvent

    data class OnAsignaturaClick(val id: Int) : ListAsignaturaUiEvent

    data object OnAddClick : ListAsignaturaUiEvent

    data class OnDeleteAsignatura(val id: Int) : ListAsignaturaUiEvent

    data class OnSaveAsignatura(val asignatura: Asignatura) : ListAsignaturaUiEvent
}
