package com.example.registrodeestudiantes.presentacion.tipopenalidad.list
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad


data class ListTipoPenalidadUiState(
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)