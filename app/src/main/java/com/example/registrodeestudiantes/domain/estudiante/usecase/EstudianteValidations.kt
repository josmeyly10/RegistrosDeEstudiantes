package com.example.registrodeestudiantes.domain.estudiante.usecase
import com.example.registrodeestudiantes.domain.estudiante.model.Estudiante
import com.example.registrodeestudiantes.domain.estudiante.repository.EstudianteRepository
import javax.inject.Inject

class EstudianteValidationsUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {

    suspend fun validateNombres(value: String, currentId: Int = 0): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("Los nombres no pueden estar vacíos")
            )
            repository.existsByNombres(value.trim(), currentId) -> Result.failure(
                IllegalArgumentException("Ya existe un estudiante con ese nombre")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateEmail(value: String): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El email no puede estar vacío")
            )
            !value.contains("@") -> Result.failure(
                IllegalArgumentException("El email debe contener @")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateEdad(value: String): Result<Int> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("La edad no puede estar vacía")
            )
            value.toIntOrNull() == null -> Result.failure(
                IllegalArgumentException("La edad debe ser un número válido")
            )
            else -> Result.success(value.toInt())
        }
    }
}