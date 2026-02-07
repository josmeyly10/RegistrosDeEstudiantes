package com.example.registrodeestudiantes.presentacion.tipopenalidad.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.DeleteTipoPenalidadUseCase
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.GetTipoPenalidadUseCase
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.TipoPenalidadValidationsUseCase
import com.example.registrodeestudiantes.domain.tipopenalidad.usecase.UpsertTipoPenalidadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    private val validations: TipoPenalidadValidationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state: StateFlow<EditTipoPenalidadUiState> = _state.asStateFlow()

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.Load -> onLoad(id = event.id)

            is EditTipoPenalidadUiEvent.NombreChanged -> _state.value = _state.value.copy(
                nombre = event.value,
                nombreError = null
            )

            is EditTipoPenalidadUiEvent.DescripcionChanged -> _state.value = _state.value.copy(
                descripcion = event.value,
                descripcionError = null
            )

            is EditTipoPenalidadUiEvent.PuntosDescuentoChanged -> _state.value = _state.value.copy(
                puntosDescuento = event.value,
                puntosDescuentoError = null
            )

            EditTipoPenalidadUiEvent.Save -> onSave()

            EditTipoPenalidadUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.value = EditTipoPenalidadUiState(isNew = true)
            return
        }

        viewModelScope.launch {
            val tipoPenalidad = getTipoPenalidadUseCase(id)
            if (tipoPenalidad != null) {
                _state.value = EditTipoPenalidadUiState(
                    tipoId = tipoPenalidad.tipoId,
                    nombre = tipoPenalidad.nombre,
                    descripcion = tipoPenalidad.descripcion,
                    puntosDescuento = tipoPenalidad.puntosDescuento.toString(),
                    isNew = false
                )
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val nombre = _state.value.nombre
            val descripcion = _state.value.descripcion
            val puntosDescuento = _state.value.puntosDescuento
            val currentId = _state.value.tipoId ?: 0

            val nombreValidation = validations.validateNombre(nombre, currentId)
            val descripcionValidation = validations.validateDescripcion(descripcion)
            val puntosDescuentoValidation = validations.validatePuntosDescuento(puntosDescuento)

            if (
                nombreValidation.isFailure ||
                descripcionValidation.isFailure ||
                puntosDescuentoValidation.isFailure
            ) {
                _state.value = _state.value.copy(
                    nombreError = nombreValidation.exceptionOrNull()?.message,
                    descripcionError = descripcionValidation.exceptionOrNull()?.message,
                    puntosDescuentoError = puntosDescuentoValidation.exceptionOrNull()?.message
                )
                return@launch
            }

            _state.value = _state.value.copy(isSaving = true)

            val tipoPenalidad = TipoPenalidad(
                tipoId = currentId,
                nombre = nombre,
                descripcion = descripcion,
                puntosDescuento = puntosDescuento.toInt()
            )

            val result = upsertTipoPenalidadUseCase(tipoPenalidad)

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
        val id = _state.value.tipoId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isDeleting = true)

            deleteTipoPenalidadUseCase(id)

            _state.value = _state.value.copy(
                isDeleting = false,
                deleted = true
            )
        }
    }
}