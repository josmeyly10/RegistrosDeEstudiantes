package com.example.registrodeestudiantes.presentacion.estudiante.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.usecase.DeleteEstudianteUseCase
import com.example.registrodeestudiantes.domain.estudiante.usecase.EstudianteValidationsUseCase
import com.example.registrodeestudiantes.domain.estudiante.usecase.GetEstudianteUseCase
import com.example.registrodeestudiantes.domain.estudiante.usecase.UpsertEstudianteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    private val validations: EstudianteValidationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.Load -> onLoad(id = event.id)

            is EditEstudianteUiEvent.NombresChanged -> _state.value = _state.value.copy(
                nombres = event.value,
                nombresError = null
            )

            is EditEstudianteUiEvent.EmailChanged -> _state.value = _state.value.copy(
                email = event.value,
                emailError = null
            )

            is EditEstudianteUiEvent.EdadChanged -> _state.value = _state.value.copy(
                edad = event.value,
                edadError = null
            )

            EditEstudianteUiEvent.Save -> onSave()

            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.value = EditEstudianteUiState(isNew = true)
            return
        }

        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            if (estudiante != null) {
                _state.value = EditEstudianteUiState(
                    estudianteId = estudiante.estudianteId,
                    nombres = estudiante.nombres,
                    email = estudiante.email,
                    edad = estudiante.edad.toString(),
                    isNew = false
                )
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val nombres = _state.value.nombres
            val email = _state.value.email
            val edad = _state.value.edad
            val currentId = _state.value.estudianteId ?: 0


            val nombresValidation = validations.validateNombres(nombres, currentId)


            val emailValidation = validations.validateEmail(email)


            val edadValidation = validations.validateEdad(edad)

            if (nombresValidation.isFailure || emailValidation.isFailure || edadValidation.isFailure) {
                _state.value = _state.value.copy(
                    nombresError = nombresValidation.exceptionOrNull()?.message,
                    emailError = emailValidation.exceptionOrNull()?.message,
                    edadError = edadValidation.exceptionOrNull()?.message
                )
                return@launch
            }

            _state.value = _state.value.copy(isSaving = true)

            val estudiante = Estudiante(
                estudianteId = currentId,
                nombres = nombres,
                email = email,
                edad = edad.toInt()
            )

            val result = upsertEstudianteUseCase(estudiante)

            result.onSuccess {
                _state.value = _state.value.copy(
                    isSaving = false,
                    saved = true
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(isSaving = false)
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.estudianteId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isDeleting = true)

            deleteEstudianteUseCase(id)

            _state.value = _state.value.copy(
                isDeleting = false,
                deleted = true
            )
        }
    }
}