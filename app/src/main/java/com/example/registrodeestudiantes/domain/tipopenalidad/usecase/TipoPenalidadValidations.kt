package com.example.registrodeestudiantes.domain.tipopenalidad.usecase
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class TipoPenalidadValidationsUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {

    suspend fun validateNombre(value: String, currentId: Int = 0): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El nombre no puede estar vacío")
            )
            repository.existsByNombre(value.trim(), currentId) -> Result.failure(
                IllegalArgumentException("Ya existe un tipo de penalidad " +
                        "registrado con este nombre")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateDescripcion(value: String): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("La descripción no puede estar vacía")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validatePuntosDescuento(value: String): Result<Int> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("Los puntos de descuento no pueden estar vacíos")
            )
            value.toIntOrNull() == null -> Result.failure(
                IllegalArgumentException("Los puntos de descuento deben ser un número válido")
            )
            value.toInt() <= 0 -> Result.failure(
                IllegalArgumentException("Los puntos de descuento deben ser mayor a cero")
            )
            else -> Result.success(value.toInt())
        }
    }
}