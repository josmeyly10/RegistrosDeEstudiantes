package com.example.registrodeestudiantes.presentacion.asignatura.list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import com.example.registrodeestudiantes.domain.asignatura.usecase.DeleteAsignaturaUseCase
import com.example.registrodeestudiantes.domain.asignatura.usecase.UpsertAsignaturaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    private val repository: AsignaturaRepository,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _state = MutableStateFlow(ListAsignaturaUiState())
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        loadAsignaturas()
    }

    fun onEvent(event: ListAsignaturaUiEvent) {
        when (event) {
            is ListAsignaturaUiEvent.SearchQueryChanged -> {
                _searchQuery.value = event.query
                _state.value = _state.value.copy(searchQuery = event.query)
            }

            is ListAsignaturaUiEvent.OnAsignaturaClick -> {

            }

            is ListAsignaturaUiEvent.OnAddClick -> {

            }

            is ListAsignaturaUiEvent.OnDeleteAsignatura -> {
                deleteAsignatura(event.id)
            }

            is ListAsignaturaUiEvent.OnSaveAsignatura -> {
                saveAsignatura(event.asignatura)
            }
        }
    }

    private fun loadAsignaturas() {
        viewModelScope.launch {
            combine(
                repository.observeAll(),
                _searchQuery
            ) { asignaturas, query ->
                if (query.isBlank()) {
                    asignaturas
                } else {
                    asignaturas.filter {
                        it.codigo.contains(query, ignoreCase = true) ||
                                it.nombre.contains(query, ignoreCase = true)
                    }
                }
            }.collect { filteredAsignaturas ->
                _state.value = _state.value.copy(
                    asignaturas = filteredAsignaturas,
                    isLoading = false
                )
            }
        }
    }

    private fun saveAsignatura(asignatura: Asignatura) {
        viewModelScope.launch {
            upsertAsignaturaUseCase(asignatura)
        }
    }

    private fun deleteAsignatura(id: Int) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(id)
        }
    }
}
