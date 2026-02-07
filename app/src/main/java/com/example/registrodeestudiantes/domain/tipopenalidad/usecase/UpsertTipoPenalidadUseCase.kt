package com.example.registrodeestudiantes.domain.tipopenalidad.usecase
import com.example.registrodeestudiantes.domain.tipopenalidad.model.TipoPenalidad
import com.example.registrodeestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject


class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository,
    ) {
        private val validations = TipoPenalidadValidationsUseCase(repository)

        suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Unit> {

            val nombreResult = validations.validateNombre(
                value = tipoPenalidad.nombre,
                currentId = tipoPenalidad.tipoId
            )
            if (nombreResult.isFailure) {
                return Result.failure(
                    IllegalArgumentException(nombreResult.exceptionOrNull()?.message)
                )
            }

            val descripcionResult = validations.validateDescripcion(value = tipoPenalidad.descripcion)
            if (descripcionResult.isFailure) {
                return Result.failure(
                    IllegalArgumentException(descripcionResult.exceptionOrNull()?.message)
                )
            }

            val puntosDescuentoResult = validations.validatePuntosDescuento(value = tipoPenalidad.puntosDescuento.toString())
            if (puntosDescuentoResult.isFailure) {
                return Result.failure(
                    IllegalArgumentException(puntosDescuentoResult.exceptionOrNull()?.message)
                )
            }

            return runCatching {
                repository.upsert(tipoPenalidad)
            }
        }
    }
