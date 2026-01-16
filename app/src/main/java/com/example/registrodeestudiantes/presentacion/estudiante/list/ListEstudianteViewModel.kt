package com.example.registrodeestudiantes.presentacion.estudiante.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import com.example.registrodeestudiantes.domain.estudiante.usecase.DeleteEstudianteUseCase
import com.example.registrodeestudiantes.domain.estudiante.usecase.UpsertEstudianteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEstudianteViewModel @Inject constructor(
    private val repository: EstudianteRepository,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _state = MutableStateFlow(ListEstudianteUiState())
    val state: StateFlow<ListEstudianteUiState> = _state.asStateFlow()

    init {
        loadEstudiantes()
    }

    fun onEvent(event: ListEstudianteUiEvent) {
        when (event) {
            is ListEstudianteUiEvent.SearchQueryChanged -> {
                _searchQuery.value = event.query
                _state.value = _state.value.copy(searchQuery = event.query)
            }
            is ListEstudianteUiEvent.OnEstudianteClick -> {

            }
            is ListEstudianteUiEvent.OnAddClick -> {

            }
            is ListEstudianteUiEvent.OnDeleteEstudiante -> {
                deleteEstudiante(event.id)
            }
            is ListEstudianteUiEvent.OnSaveEstudiante -> {
                saveEstudiante(event.estudiante)
            }
        }
    }

    private fun loadEstudiantes() {
        viewModelScope.launch {
            combine(
                repository.observeAll(),
                _searchQuery
            ) { estudiantes, query ->
                if (query.isBlank()) {
                    estudiantes
                } else {
                    estudiantes.filter {
                        it.nombres.contains(query, ignoreCase = true) ||
                                it.email.contains(query, ignoreCase = true)
                    }
                }
            }.collect { filteredEstudiantes ->
                _state.value = _state.value.copy(
                    estudiantes = filteredEstudiantes,
                    isLoading = false
                )
            }
        }
    }

    private fun saveEstudiante(estudiante: Estudiante) {
        viewModelScope.launch {
            upsertEstudianteUseCase(estudiante)
        }
    }

    private fun deleteEstudiante(id: Int) {
        viewModelScope.launch {
            deleteEstudianteUseCase(id)
        }
    }
}

