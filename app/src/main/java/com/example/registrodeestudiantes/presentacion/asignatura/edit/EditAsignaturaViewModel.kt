package com.example.registrodeestudiantes.presentacion.asignatura.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.asignatura.model.Asignatura
import com.example.registrodeestudiantes.domain.asignatura.usecase.AsignaturaValidationsUseCase
import com.example.registrodeestudiantes.domain.asignatura.usecase.DeleteAsignaturaUseCase
import com.example.registrodeestudiantes.domain.asignatura.usecase.GetAsignaturaUseCase
import com.example.registrodeestudiantes.domain.asignatura.usecase.UpsertAsignaturaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    private val validations: AsignaturaValidationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.Load -> onLoad(id = event.id)

            is EditAsignaturaUiEvent.CodigoChanged -> _state.value = _state.value.copy(
                codigo = event.value,
                codigoError = null
            )

            is EditAsignaturaUiEvent.NombreChanged -> _state.value = _state.value.copy(
                nombre = event.value,
                nombreError = null
            )

            is EditAsignaturaUiEvent.AulaChanged -> _state.value = _state.value.copy(
                aula = event.value,
                aulaError = null
            )

            is EditAsignaturaUiEvent.CreditosChanged -> _state.value = _state.value.copy(
                creditos = event.value,
                creditosError = null
            )

            EditAsignaturaUiEvent.Save -> onSave()

            EditAsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.value = EditAsignaturaUiState(isNew = true)
            return
        }

        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            if (asignatura != null) {
                _state.value = EditAsignaturaUiState(
                    asignaturaId = asignatura.asignaturaId,
                    codigo = asignatura.codigo,
                    nombre = asignatura.nombre,
                    aula = asignatura.aula,
                    creditos = asignatura.creditos.toString(),
                    isNew = false
                )
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val codigo = _state.value.codigo
            val nombre = _state.value.nombre
            val aula = _state.value.aula
            val creditos = _state.value.creditos
            val currentId = _state.value.asignaturaId ?: 0

            val codigoValidation = validations.validateCodigo(codigo, currentId)
            val nombreValidation = validations.validateNombre(nombre)
            val aulaValidation = validations.validateAula(aula)
            val creditosValidation = validations.validateCreditos(creditos)

            if (
                codigoValidation.isFailure ||
                nombreValidation.isFailure ||
                aulaValidation.isFailure ||
                creditosValidation.isFailure
            ) {
                _state.value = _state.value.copy(
                    codigoError = codigoValidation.exceptionOrNull()?.message,
                    nombreError = nombreValidation.exceptionOrNull()?.message,
                    aulaError = aulaValidation.exceptionOrNull()?.message,
                    creditosError = creditosValidation.exceptionOrNull()?.message
                )
                return@launch
            }

            _state.value = _state.value.copy(isSaving = true)

            val asignatura = Asignatura(
                asignaturaId = currentId,
                codigo = codigo,
                nombre = nombre,
                aula = aula,
                creditos = creditos.toInt()
            )

            val result = upsertAsignaturaUseCase(asignatura)

            result.onSuccess {
                _state.value = _state.value.copy(
                    isSaving = false,
                    saved = true
                )
            }.onFailure {
                _state.value = _state.value.copy(isSaving = false)
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.asignaturaId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isDeleting = true)

            deleteAsignaturaUseCase(id)

            _state.value = _state.value.copy(
                isDeleting = false,
                deleted = true
            )
        }
    }
}
