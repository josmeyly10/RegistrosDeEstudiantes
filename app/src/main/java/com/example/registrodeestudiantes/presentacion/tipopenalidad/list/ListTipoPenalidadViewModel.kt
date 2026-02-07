package com.example.registrodeestudiantes.presentacion.tipopenalidad.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.DeleteTipoPenalidadUseCase
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.UpsertTipoPenalidadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    private val repository: TipoPenalidadRepository,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _state = MutableStateFlow(ListTipoPenalidadUiState())
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        loadTiposPenalidades()
    }

    fun onEvent(event: ListTipoPenalidadUiEvent) {
        when (event) {
            is ListTipoPenalidadUiEvent.SearchQueryChanged -> {
                _searchQuery.value = event.query
                _state.value = _state.value.copy(searchQuery = event.query)
            }

            is ListTipoPenalidadUiEvent.OnTipoPenalidadClick -> {

            }

            is ListTipoPenalidadUiEvent.OnAddClick -> {

            }

            is ListTipoPenalidadUiEvent.OnDeleteTipoPenalidad -> {
                deleteTipoPenalidad(event.id)
            }

            is ListTipoPenalidadUiEvent.OnSaveTipoPenalidad -> {
                saveTipoPenalidad(event.tipoPenalidad)
            }
        }
    }

    private fun loadTiposPenalidades() {
        viewModelScope.launch {
            combine(
                repository.observeAll(),
                _searchQuery
            ) { tiposPenalidades, query ->
                if (query.isBlank()) {
                    tiposPenalidades
                } else {
                    tiposPenalidades.filter {
                        it.nombre.contains(query, ignoreCase = true)
                    }
                }
            }.collect { filteredTiposPenalidades ->
                _state.value = _state.value.copy(
                    tiposPenalidades = filteredTiposPenalidades,
                    isLoading = false
                )
            }
        }
    }

    private fun saveTipoPenalidad(tipoPenalidad: TipoPenalidad) {
        viewModelScope.launch {
            upsertTipoPenalidadUseCase(tipoPenalidad)
        }
    }

    private fun deleteTipoPenalidad(id: Int) {
        viewModelScope.launch {
            deleteTipoPenalidadUseCase(id)
        }
    }
}