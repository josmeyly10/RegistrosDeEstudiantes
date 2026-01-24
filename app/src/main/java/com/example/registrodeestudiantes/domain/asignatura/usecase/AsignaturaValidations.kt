package com.example.registrodeestudiantes.domain.asignatura.usecase
import com.example.registrodeestudiantes.domain.asignatura.repository.AsignaturaRepository
import javax.inject.Inject

class AsignaturaValidationsUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {

    suspend fun validateCodigo(value: String, currentId: Int = 0): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El código no puede estar vacío")
            )
            repository.existsByCodigo(value.trim(), currentId) -> Result.failure(
                IllegalArgumentException("Ya existe una asignatura con ese código")
            )
            else -> Result.success(value.trim())
        }
    }

    suspend fun validateNombre(value: String, currentId: Int = 0): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El nombre no puede estar vacío")
            )
            repository.existsByNombre(value.trim(), currentId) -> Result.failure(
                IllegalArgumentException("Ya existe una asignatura registrada con este nombre")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateAula(value: String): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El aula no puede estar vacía")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateCreditos(value: String): Result<Int> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("Los créditos no pueden estar vacíos")
            )
            value.toIntOrNull() == null -> Result.failure(
                IllegalArgumentException("Los créditos deben ser un número válido")
            )
            value.toInt() <= 0 -> Result.failure(
                IllegalArgumentException("Los créditos deben ser mayores que 0")
            )
            else -> Result.success(value.toInt())
        }
    }
}