package com.example.registrodeestudiantes.presentacion.tipopenalidad.list
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad


sealed interface ListTipoPenalidadUiEvent {

    data class SearchQueryChanged(val query: String) : ListTipoPenalidadUiEvent

    data class OnTipoPenalidadClick(val id: Int) : ListTipoPenalidadUiEvent

    data object OnAddClick : ListTipoPenalidadUiEvent

    data class OnDeleteTipoPenalidad(val id: Int) : ListTipoPenalidadUiEvent

    data class OnSaveTipoPenalidad(val tipoPenalidad: TipoPenalidad) : ListTipoPenalidadUiEvent
}